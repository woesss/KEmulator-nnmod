package com.nokia.mid.sound;

import emulator.Emulator;
import emulator.media.b;

import java.io.ByteArrayInputStream;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerImpl;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.MIDIControl;
import javax.microedition.media.control.VolumeControlImpl;

public class Sound {
    public static final int SOUND_PLAYING = 0;
    public static final int SOUND_STOPPED = 1;
    public static final int SOUND_UNINITIALIZED = 3;
    public static final int FORMAT_TONE = 1;
    public static final int FORMAT_WAV = 5;
    public PlayerImpl m_player;
    private Sound inst;
    private SoundListener soundListener;
    private int state;
    private int type;
    public int dataLen;

    private final PlayerListener playerListener = new PlayerListener() {
        public void playerUpdate(Player p0, String p1, Object p2) {
            if (Sound.this.soundListener == null) return;
            if ("endOfMedia".equals(p1)) {
                Sound.this.soundListener.soundStateChanged(Sound.this, 1);
            }
        }
    };
    private byte[] data;

    public Sound(byte[] paramArrayOfByte, int paramInt) {
        this.type = paramInt;
        this.dataLen = paramArrayOfByte.length;
        this.inst = this;
        init(paramArrayOfByte, paramInt);
    }

    public String getType() {
        if (this.type == 1) {
            return "FORMAT_TONE";
        }
        if (this.type == 5) {
            return "FORMAT_WAV";
        }
        return null;
    }

    public Sound(int paramInt, long paramLong) {
        init(paramInt, paramLong);
    }

    public static int getConcurrentSoundCount(int paramInt) {
        return 1;
    }

    public int getGain() {
        return ((VolumeControlImpl) this.m_player.getControl("VolumeControl")).getLevel();
    }

    public int getState() {
        return this.state;
    }

    public static int[] getSupportedFormats() {
        int[] arrayOfInt;
        return arrayOfInt = new int[]{1, 5};
    }

    public void init(byte[] paramArrayOfByte, int paramInt) {
        if (paramArrayOfByte == null) {
            throw new NullPointerException();
        }
        if (paramInt == 1) {
            paramArrayOfByte = new b(paramArrayOfByte).method726();
        } else {
            data = paramArrayOfByte;
        }
        try {
            ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
            this.m_player = new PlayerImpl(localByteArrayInputStream, type == FORMAT_WAV ? "audio/wav" : null);
            this.m_player.addPlayerListener(playerListener);
            localByteArrayInputStream.close();
        } catch (Exception ignored) {
        }
        this.state = 3;
    }

    public void init(int paramInt, long paramLong) {
        Emulator.getEmulator().getLogStream().println("** com.nokia.mid.sound.init(int freq, long duration) not implemented yet **");
    }

    public void play(int paramInt) {
        this.m_player.setLoopCount(paramInt == 0 ? -1 : paramInt);
        resume();
    }

    public void release() {
        data = null;
        if (this.state == 0) {
            stop();
        }
        if (this.state != 3) {
            if (m_player != null) this.m_player.deallocate();
            this.state = 3;
            if (this.soundListener != null) {
                this.soundListener.soundStateChanged(this, 3);
            }
        }
    }

    public void resume() {
        try {
            this.m_player.start();
            if(type == FORMAT_TONE) {
                ((MIDIControl) m_player.getControl("MIDIControl")).setChannelVolume(0, 64);
            }
        } catch (Exception ignored) {
        }
        this.state = 0;
        if (this.soundListener != null) {
            this.soundListener.soundStateChanged(this, 0);
        }
    }

    public void setGain(int paramInt) {
        this.m_player.setLevel(paramInt);
    }

    public void setSoundListener(SoundListener paramSoundListener) {
        this.soundListener = paramSoundListener;
    }

    public void stop() {
        try {
            this.m_player.stop();
        } catch (Exception ignored) {
        }
        this.state = 1;
        if (this.soundListener != null) {
            this.soundListener.soundStateChanged(this, 1);
        }
    }

    public byte[] getData() {
        if (m_player == null) return null;
        if (data != null) return data.clone();
        return m_player.getData();
    }

    public String getExportName() {
        switch (type) {
            case FORMAT_TONE:
                return "nokiatoneconverted" + hashCode() + ".mid";
            case FORMAT_WAV:
                return "nokiaaudio" + hashCode() + ".wav";
        }
        return "nokiaaudio" + m_player.getExportName();
    }
}
