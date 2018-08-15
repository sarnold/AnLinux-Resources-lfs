# LinuxOnAndroid
Run Linux On Android Without Root Access, thanks for the Awesome [Termux](https://github.com/termux/termux-app) and [PRoot](https://github.com/proot-me/PRoot), which make this project possible.



## How it works

The bash script download image over internet, then decompress the image and then mount it using [PRoot](https://github.com/proot-me/PRoot).



## Bootstraping System

Script located it Scripts/Bootstrap are used to bootstrap the system, to bootstrap a system, simply run:

> ./bootstrap.sh <architecture> /path/to/bootstrap
   
For example: 

> ./bootstrap.sh armhf /home/user/debian/armhf   



## Note

1. This app required [Termux](https://github.com/termux/termux-app) to work, it could be install on Play Store.

2. About device requirement:

   Android Version : At lease Android Lollipop

   Architeture : armv7, arm64, x86, x86_64

3. Currently only work for command line, GUI are still in progress, please do not blame developers for this.

4. Currently only Ubuntu, Debian, Kali are supported, more distro are expected soon.

5. For any suggestion or issue, please open an issue on Github.



## Extra License

The author of application icon is [Alpár-Etele Méder](https://www.iconfinder.com/pocike)



## Todo

1. GUI: XSDL works, but could not perform any clicking action(Maybe because the application is too old), XRDP does not work, VNC not test yet.

2. Other Distro Support



## Reference

1. [GNURootDebian](https://github.com/corbinlc/GNURootDebian)
2. [debian-noroot](https://github.com/pelya/debian-noroot)
3. [termux-ubuntu](https://github.com/Neo-Oli/termux-ubuntu)
