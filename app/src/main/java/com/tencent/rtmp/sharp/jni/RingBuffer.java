package com.tencent.rtmp.sharp.jni;

public class RingBuffer
{
    private final int DEFAULT_SIZE = 1000;
    public int c_totalSize;
    public byte[] m_pBuf;
    public int m_read;
    public int m_write;
    public boolean m_isEmpty;
    
    public RingBuffer() {
        this.c_totalSize = 1000;
        this.m_read = 0;
        this.m_write = 0;
        this.m_isEmpty = true;
        this.m_pBuf = null;
        this.c_totalSize = 1000;
        this.m_isEmpty = true;
        this.m_read = 0;
        this.m_write = 0;
        this.m_pBuf = new byte[this.c_totalSize];
    }
    
    public RingBuffer(final int c_totalSize) {
        this.c_totalSize = 1000;
        this.m_read = 0;
        this.m_write = 0;
        this.m_isEmpty = true;
        this.c_totalSize = c_totalSize;
        this.m_isEmpty = true;
        this.m_read = 0;
        this.m_write = 0;
        this.m_pBuf = new byte[this.c_totalSize];
    }
    
    public void Push(final byte[] array, final int n) {
        if (this.m_pBuf == null) {
            return;
        }
        if (this.RemainWrite() < n) {
            return;
        }
        if (this.c_totalSize - this.m_write >= n) {
            System.arraycopy(array, 0, this.m_pBuf, this.m_write, n);
        }
        else {
            System.arraycopy(array, 0, this.m_pBuf, this.m_write, this.c_totalSize - this.m_write);
            System.arraycopy(array, this.c_totalSize - this.m_write, this.m_pBuf, 0, n - (this.c_totalSize - this.m_write));
        }
        this.m_write = (this.m_write + n) % this.c_totalSize;
        this.m_isEmpty = false;
    }
    
    public int RemainRead() {
        if (this.m_write < this.m_read) {
            return this.c_totalSize - this.m_read + this.m_write;
        }
        if (this.m_write > this.m_read) {
            return this.m_write - this.m_read;
        }
        return this.m_isEmpty ? 0 : this.c_totalSize;
    }
    
    public int RemainWrite() {
        return this.c_totalSize - this.RemainRead();
    }
    
    public void Clear() {
        this.m_write = 0;
        this.m_read = this.m_write;
        this.m_isEmpty = true;
    }
    
    public boolean Pop(final byte[] array, final int n) {
        if (this.m_pBuf == null) {
            return false;
        }
        if (this.RemainRead() < n || n <= 0) {
            return false;
        }
        if (this.c_totalSize - this.m_read >= n) {
            System.arraycopy(this.m_pBuf, this.m_read, array, 0, n);
        }
        else {
            System.arraycopy(this.m_pBuf, this.m_read, array, 0, this.c_totalSize - this.m_read);
            System.arraycopy(this.m_pBuf, 0, array, this.c_totalSize - this.m_read, n - (this.c_totalSize - this.m_read));
        }
        this.m_read = (this.m_read + n) % this.c_totalSize;
        this.m_isEmpty = (this.m_read == this.m_write);
        return true;
    }
}
