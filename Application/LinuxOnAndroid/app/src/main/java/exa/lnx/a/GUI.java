package exa.lnx.a;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class GUI extends Fragment {

    Context context;
    Button button;
    Button button2;
    Button button3;
    TextView textView2;
    TextView textView3;
    String distro;
    String s;
    boolean shouldShowAds;
    InterstitialAd mInterstitialAd;
    AdView mAdView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        getActivity().setTitle(R.string.gui_title);

        View view = inflater.inflate(R.layout.gui, container, false);

        context = getActivity().getApplicationContext();

        distro = "Nothing";

        s = Build.SUPPORTED_ABIS[0];

        shouldShowAds = false;

        button = view.findViewById(R.id.button);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);

        textView2 = view.findViewById(R.id.textView2);
        textView3 = view.findViewById(R.id.textView3);

        textView2.setText("Step 2 : Please choose a distro first");
        textView3.setText("Step 3 : Please choose a distro first.");
        button2.setEnabled(false);
        button3.setEnabled(false);

        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-5748356089815497/3581271493");

        mAdView = view.findViewById(R.id.adView);

        if(!donationInstalled()){
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            mAdView.loadAd(new AdRequest.Builder().build());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyUserToChooseDistro();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                if(distro.equals("Ubuntu")){
                    ClipData clip = ClipData.newPlainText("Command", "apt-get update && apt-get install wget xterm tightvncserver -y && wget https://raw.githubusercontent.com/EXALAB/LinuxOnAndroid/master/Scripts/GUI/Apt/gui.sh && bash gui.sh");
                    clipboard.setPrimaryClip(clip);
                }else if(distro.equals("Debian")){
                    ClipData clip = ClipData.newPlainText("Command", "apt-get update && apt-get install wget xterm tightvncserver -y && wget https://raw.githubusercontent.com/EXALAB/LinuxOnAndroid/master/Scripts/GUI/Apt/gui.sh && bash gui.sh");
                    clipboard.setPrimaryClip(clip);
                }else if(distro.equals("Kali")){
                    ClipData clip = ClipData.newPlainText("Command", "apt-get update && apt-get install wget xterm tightvncserver -y && wget https://raw.githubusercontent.com/EXALAB/LinuxOnAndroid/master/Scripts/GUI/Apt/gui.sh && bash gui.sh");
                    clipboard.setPrimaryClip(clip);
                }else if(distro.equals("Fedora")){
                    ClipData clip = ClipData.newPlainText("Command", "yum install wget xterm tigervnc-server -y && wget https://raw.githubusercontent.com/EXALAB/LinuxOnAndroid/master/Scripts/GUI/Yum/gui.sh && bash gui.sh");
                    clipboard.setPrimaryClip(clip);
                }else if(distro.equals("CentOS")){
                    ClipData clip = ClipData.newPlainText("Command", "yum install wget xterm tigervnc-server -y && wget https://raw.githubusercontent.com/EXALAB/LinuxOnAndroid/master/Scripts/GUI/Yum/gui.sh && bash gui.sh");
                    clipboard.setPrimaryClip(clip);
                }
                //else if(distro.equals("Leap")){
                    //ClipData clip = ClipData.newPlainText("Command", "pkg install wget proot tar -y && wget https://raw.githubusercontent.com/EXALAB/LinuxOnAndroid/master/Scripts/Installer/openSUSE/Leap/opensuse-leap.sh && bash opensuse-leap.sh");
                    //clipboard.setPrimaryClip(clip);
                //}else if(distro.equals("Tumbleweed")){
                    //ClipData clip = ClipData.newPlainText("Command", "pkg install wget proot tar -y && wget https://raw.githubusercontent.com/EXALAB/LinuxOnAndroid/master/Scripts/Installer/openSUSE/Tumbleweed/opensuse-tumbleweed.sh && bash opensuse-tumbleweed.sh");
                    //clipboard.setPrimaryClip(clip);
                //}
                if(mInterstitialAd != null && mInterstitialAd.isLoaded() && shouldShowAds){
                    if(!donationInstalled()){
                        mInterstitialAd.show();
                    }
                    shouldShowAds = false;
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.termux");
                if(isPackageInstalled("com.termux", context.getPackageManager())){
                    startActivity(intent);
                }else{
                    notifyUserForInstallTerminal();
                }
            }
        });
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        return view;
    }
    public void notifyUserToChooseDistro(){
        final ViewGroup nullParent = null;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.distro_chooser, nullParent);
        final CheckBox checkBox = view.findViewById(R.id.checkBox);
        final CheckBox checkBox2 = view.findViewById(R.id.checkBox2);
        final CheckBox checkBox3 = view.findViewById(R.id.checkBox3);
        final CheckBox checkBox4 = view.findViewById(R.id.checkBox4);
        final CheckBox checkBox5 = view.findViewById(R.id.checkBox5);
        final CheckBox checkBox6 = view.findViewById(R.id.checkBox6);
        final CheckBox checkBox7 = view.findViewById(R.id.checkBox7);


        alertDialog.setView(view);
        alertDialog.setCancelable(false);

        if(distro.equals("Ubuntu")){
            checkBox.setChecked(true);
        }else if(distro.equals("Debian")){
            checkBox2.setChecked(true);
        }else if(distro.equals("Kali")){
            checkBox3.setChecked(true);
        }else if(distro.equals("Fedora")){
            checkBox4.setChecked(true);
        }else if(distro.equals("CentOS")){
            checkBox5.setChecked(true);
        }
        //else if(distro.equals("Leap")){
            //checkBox6.setChecked(true);
        //}else if(distro.equals("Tumbleweed")){
            //checkBox7.setChecked(true);
        //}

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                checkBox5.setChecked(false);
                //checkBox6.setChecked(false);
                //checkBox7.setChecked(false);
            }
        });
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                checkBox5.setChecked(false);
                //checkBox6.setChecked(false);
                //checkBox7.setChecked(false);
            }
        });
        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox.setChecked(false);
                checkBox2.setChecked(false);
                checkBox4.setChecked(false);
                checkBox5.setChecked(false);
                //checkBox6.setChecked(false);
                //checkBox7.setChecked(false);
            }
        });
        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox.setChecked(false);
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox5.setChecked(false);
                //checkBox6.setChecked(false);
                //checkBox7.setChecked(false);
            }
        });
        checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox.setChecked(false);
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
                checkBox4.setChecked(false);
                //checkBox6.setChecked(false);
                //checkBox7.setChecked(false);
            }
        });
        //checkBox6.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {
                //checkBox.setChecked(false);
                //checkBox2.setChecked(false);
                //checkBox3.setChecked(false);
                //checkBox4.setChecked(false);
                //checkBox5.setChecked(false);
                // //checkBox7.setChecked(false);
            //}
        //});
        //checkBox7.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {
                //checkBox.setChecked(false);
                //checkBox2.setChecked(false);
                //checkBox3.setChecked(false);
                //checkBox4.setChecked(false);
                //checkBox5.setChecked(false);
                // //checkBox6.setChecked(false);
            //}
        //});
        if(s.equals("i386")){
            checkBox4.setEnabled(false);
            checkBox6.setEnabled(false);
            checkBox7.setEnabled(false);
            checkBox4.setText("Not supported");
            checkBox6.setText("Not supported");
            checkBox7.setText("Not supported");
        }else if (s.equals("armeabi") | s.equals("armeabi-v7a")){
            checkBox6.setEnabled(false);
            checkBox7.setEnabled(false);
            checkBox6.setText("Not supported");
            checkBox7.setText("Not supported");
        }else{
            checkBox6.setEnabled(false);
            checkBox7.setEnabled(false);
            checkBox6.setText("openSUSE Leap (Not yet supported)");
            checkBox7.setText("openSUSE Tumbleweed (Not yet supported)");
        }
        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(checkBox.isChecked()){
                    if(!distro.equals("Ubuntu")){
                        shouldShowAds = true;
                        distro = "Ubuntu";
                    }
                }else if(checkBox2.isChecked()){
                    if(!distro.equals("Debian")){
                        shouldShowAds = true;
                        distro = "Debian";
                    }
                }else if(checkBox3.isChecked()){
                    if(!distro.equals("Kali")){
                        shouldShowAds = true;
                        distro = "Kali";
                    }
                }else if(checkBox4.isChecked()){
                    if(!distro.equals("Fedora")){
                        shouldShowAds = true;
                        distro = "Fedora";
                    }
                }else if(checkBox5.isChecked()){
                    if(!distro.equals("CentOS")){
                        shouldShowAds = true;
                        distro = "CentOS";
                    }
                }else if(checkBox6.isChecked()){
                    if(!distro.equals("Leap")){
                        shouldShowAds = true;
                        distro = "Leap";
                    }
                }else if(checkBox7.isChecked()){
                    if(!distro.equals("Tumbleweed")){
                        shouldShowAds = true;
                        distro = "Tumbleweed";
                    }
                }
                if(distro.equals("Ubuntu")){
                    textView2.setText("Step 2 : Copy the command to clipboard : apt-get update && apt-get install wget xterm tightvncserver -y && wget https://raw.githubusercontent.com/EXALAB/LinuxOnAndroid/master/Scripts/GUI/Apt/gui.sh && bash gui.sh \n\n This should setup Tiger VNC on the Linux System.");
                    textView3.setText("Step 3 : Start Termux, paste and enter the command to install distro. Remember: you will need to run ./start-ubuntu.sh to run the Linux System before using this command.");
                }else if(distro.equals("Debian")){
                    textView2.setText("Step 2 : Copy the command to clipboard : apt-get update && apt-get install wget xterm tightvncserver -y && wget https://raw.githubusercontent.com/EXALAB/LinuxOnAndroid/master/Scripts/GUI/Apt/gui.sh && bash gui.sh \n\n This should setup Tiger VNC on the Linux System.");
                    textView3.setText("Step 3 : Start Termux, paste and enter the command to install distro. Remember: you will need to run ./start-debian.sh to run the Linux System before using this command.");
                }else if(distro.equals("Kali")){
                    textView2.setText("Step 2 : Step 2 : Copy the command to clipboard : apt-get update && apt-get install wget xterm tightvncserver -y && wget https://raw.githubusercontent.com/EXALAB/LinuxOnAndroid/master/Scripts/GUI/Apt/gui.sh && bash gui.sh \n\n This should setup Tiger VNC on the Linux System.");
                    textView3.setText("Step 3 : Start Termux, paste and enter the command to install distro. Remember: you will need to run ./start-kali.sh to run the Linux System before using this command.");
                }else if(distro.equals("Fedora")){
                    textView2.setText("Step 2 : Copy the command to clipboard : yum install wget xterm tigervnc-server -y && wget https://raw.githubusercontent.com/EXALAB/LinuxOnAndroid/master/Scripts/GUI/Yum/gui.sh && bash gui.sh \n\n This should setup Tiger VNC on the Linux System.");
                    textView3.setText("Step 3 : Start Termux, paste and enter the command to install distro. Remember: you will need to run ./start-fedora.sh to run the Linux System before using this command.");
                }else if(distro.equals("CentOS")){
                    textView2.setText("Step 2 : Copy the command to clipboard : yum install wget xterm tigervnc-server -y && wget https://raw.githubusercontent.com/EXALAB/LinuxOnAndroid/master/Scripts/GUI/Yum/gui.sh && bash gui.sh \n\n This should setup Tiger VNC on the Linux System.");
                    textView3.setText("Step 3 : Start Termux, paste and enter the command to install distro. Remember: you will need to run ./start-centos.sh to run the Linux System before using this command.");
                }

                //else if(distro.equals("Leap")){
                    //textView2.setText("Step 2 : Copy the command to clipboard : pkg install wget proot tar -y && wget https://raw.githubusercontent.com/EXALAB/LinuxOnAndroid/master/Scripts/Installer/openSUSE/Leap/opensuse-leap.sh && bash opensuse-leap.sh \n\n This should install openSUSE Leap on your system, you can then run ./start-leap.sh to run the command line.");
                    //textView3.setText("Step 3 : Start Termux, paste and enter the command to install distro. Remember: you will need to run ./start-leap.sh to run the command line.");
                //}else if(distro.equals("Tumbleweed")){
                    //textView2.setText("Step 2 : Copy the command to clipboard : pkg install wget proot tar -y && wget https://raw.githubusercontent.com/EXALAB/LinuxOnAndroid/master/Scripts/Installer/openSUSE/Tumbleweed/opensuse-tumbleweed.sh && bash opensuse-tumbleweed.sh \n\n This should install openSUSE Tumbleweed on your system, you can then run ./start-tumbleweed.sh to run the command line.");
                    //textView3.setText("Step 3 : Start Termux, paste and enter the command to install distro. Remember: you will need to run ./start-tumbleweed.sh to run the command line.");
                //}
                button2.setEnabled(true);
                button3.setEnabled(true);
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
    public void notifyUserForInstallTerminal(){
        final ViewGroup nullParent = null;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.notify1, nullParent);
        TextView textView = view.findViewById(R.id.textView);

        alertDialog.setView(view);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse("market://details?id=com.termux");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                if(Build.VERSION.SDK_INT >= 21){
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                }
                try{
                    startActivity(intent);
                }catch(ActivityNotFoundException e){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.termux")));
                }
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
        textView.setText("Termux is not installed, do you want to install it now ?");
    }
    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    private boolean donationInstalled() {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.checkSignatures(context.getPackageName(), "exa.lnx.d") == PackageManager.SIGNATURE_MATCH;
    }
}
