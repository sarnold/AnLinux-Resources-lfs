#!/data/data/com.termux/files/usr/bin/bash
folder=gentoo-fs
if [ -d "$folder" ]; then
	first=1
	echo "skipping downloading"
fi
tarball="gentoo-rootfs.tar.gz"
if [ "$first" != 1 ];then
	if [ ! -f $tarball ]; then
		echo "Download Rootfs, this may take a while based on your internet speed."
		case `dpkg --print-architecture` in
		aarch64)
			archurl="arm64" ;;
		arm)
			archurl="armv7a" ;;
		amd64)
			archurl="amd64" ;;
		x86_64)
			archurl="amd64" ;;
		i*86)
			archurl="i686" ;;
		x86)
			archurl="i686" ;;
		*)
			echo "unknown architecture"; exit 1 ;;
		esac
		wget "https://releases.orchardos.com/orchardos-${archurl}-musl-hardened-dev-20190310.tar.gz" -O $tarball
	fi
	cur=`pwd`
	mkdir -p "$folder"
	cd "$folder"
	echo "Decompressing Rootfs, please be patient."
	proot --link2symlink tar -xf ${cur}/${tarball} --exclude='dev' 2> /dev/null||:
	cd "$cur"
fi
mkdir -p gentoo-binds
bin=start-gentoo.sh
echo "writing launch script"
cat > $bin <<- EOM
#!/bin/bash
cd \$(dirname \$0)
## unset LD_PRELOAD in case termux-exec is installed
unset LD_PRELOAD
command="proot"
command+=" --link2symlink"
command+=" -0"
command+=" -r $folder"
if [ -n "\$(ls -A gentoo-binds)" ]; then
    for f in gentoo-binds/* ;do
      . \$f
    done
fi
command+=" -b /dev"
command+=" -b /proc"
command+=" -b gentoo-fs/tmp:/dev/shm"
## uncomment the following line to have access to the home directory of termux
#command+=" -b /data/data/com.termux/files/home:/root"
## uncomment the following line to mount /sdcard directly to / 
#command+=" -b /sdcard"
command+=" -w /root"
command+=" /usr/bin/env -i"
command+=" HOME=/root"
command+=" PATH=PATH=/bin:/usr/bin:/sbin:/usr/sbin"
command+=" TERM=\$TERM"
command+=" LANG=C.UTF-8"
command+=" /bin/sh --login"
com="\$@"
if [ -z "\$1" ];then
    exec \$command
else
    \$command -c "\$com"
fi
EOM

echo "fixing shebang of $bin"
termux-fix-shebang $bin
echo "making $bin executable"
chmod +x $bin
echo "removing image for some space"
rm $tarball
echo "Preparing additional component for the first time, please wait..."
rm gentoo-fs/etc/resolv.conf
wget "https://raw.githubusercontent.com/sarnold/AnLinux-Resources-lfs/master/Scripts/Installer/Gentoo/resolv.conf" -P gentoo-fs/etc
echo "You can now launch Gentoo with the ./${bin} script"
