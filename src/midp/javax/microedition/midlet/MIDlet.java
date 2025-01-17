package javax.microedition.midlet;

import emulator.*;
import emulator.custom.CustomMethod;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;

import javax.microedition.io.*;

public abstract class MIDlet {
    private boolean destroyed;

    public MIDlet() {
        super();
        Emulator.setMIDlet(this);
    }

    public int checkPermission(final String s) {
        return 1;
    }

    public String getAppProperty(final String s) {
        return Emulator.getEmulator().getAppProperty(s);
    }

    public void notifyDestroyed() {
        if (destroyed) return;
        destroyed = true;
        Emulator.getEmulator().getLogStream().println("Notify Destroyed");
        Emulator.notifyDestroyed();
        Emulator.getEmulator().getLogStream().println("Exiting Emulator");
        CustomMethod.close();
        System.exit(0);
    }

    public void notifyPaused() {
    }

    public boolean platformRequest(String url) throws ConnectionNotFoundException {
        try {
            Emulator.getEmulator().getLogStream().println("platformRequest " + url);
            if (url.startsWith("vlc.exe \"")) {
                url = "vlc:" + url.substring(9, url.length() - 1);
            }
            if (Settings.networkNotAvailable || !Permission.requestURLAccess(url)) {
                return false;
            }
            if (url.startsWith("file:///root/")) {
                url = "file:///" + (Emulator.getUserPath().replace("\\", "/") +
                        "/file/" + url.substring(8)).replace(" ", "%20");
            }
            if (url.startsWith("vlc:")) {
                url = url.substring(4);
                if (url.indexOf(':') == -1) {
                    throw new ConnectionNotFoundException("Invalid URL");
                }
                if (url.startsWith("file:///root/")) {
                    url = "file:///" + (Emulator.getUserPath().replace("\\", "/") +
                            "/file" + url.substring(13)).replace(" ", "%20");
                }
                if (Settings.vlcDir != null && Settings.vlcDir.length() > 2) {
                    Runtime.getRuntime().exec(new File(Settings.vlcDir).getCanonicalPath() + "/vlc \"" + url + "\"");
                    return false;
                }
                String vlcdir = "C:/Program Files/VideoLAN/VLC/vlc.exe";
                if (!Emulator.isX64() && new File(vlcdir).exists()) {
                    Runtime.getRuntime().exec(vlcdir + " \"" + url + "\"");
                    return false;
                }
                throw new ConnectionNotFoundException("vlc dir not set");
            }
            if (Desktop.getDesktop().isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                if (Emulator.isX64()) {
                    throw new ConnectionNotFoundException("not supported");
                }
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            }
            return false;
        } catch (ConnectionNotFoundException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectionNotFoundException(e);
        }
    }

    public void resumeRequest() {
    }

    protected abstract void destroyApp(final boolean p0) throws MIDletStateChangeException;

    protected abstract void startApp() throws MIDletStateChangeException;

    protected abstract void pauseApp();

    public void invokeDestroyApp(final boolean b) {
        try {
            this.destroyApp(b);
        } catch (Exception ex) {
            System.out.println("destroyApp exception!");
            ex.printStackTrace();
        }
        this.notifyDestroyed();
    }

    public void invokeStartApp() {
        try {
            this.startApp();
        } catch (MIDletStateChangeException ex) {
            System.out.println("startApp exception!");
            ex.printStackTrace();
        } catch (Throwable ex) {
            System.out.println("startApp exception!");
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public void invokePauseApp() {
        this.pauseApp();
    }
}
