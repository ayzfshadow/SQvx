package com.saki.qq.datapacket.pack.protobuff;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.BufferUnderflowException;

public class ProtoBuff
{
    public ProtoBuff update(byte[] data)
    {
        this.bb.release();
        this.bb=Unpooled.directBuffer();
        this.bb.writeBytes(data);
        return this;
    }

    public void destroy()
    {
        this.bb.release();
    }

    public class HeadData
    {
        public int tag;
        private ProtoBuff buff;
        public int type;

        public HeadData(ProtoBuff paramProtoBuff)
        {
            this.buff = paramProtoBuff;
        }
    }

    public ByteBuf bb;

    public ProtoBuff(byte[] paramArrayOfByte)
    {
        this.bb = Unpooled.directBuffer();
        this.bb.writeBytes(paramArrayOfByte);
    }

    private long Byte2Long(byte... paramVarArgs)
    {
        String str = "";
        for (int i = paramVarArgs.length - 1;; i--)
        {
            if (i < 0)
            {
                return new BigInteger(str, 2).longValue();
            }
            str = str + deleteHightOnebit(paramVarArgs[i]);
        }
    }

    private static long Zigzag(long paramLong)
    {
        return paramLong << 1 ^ paramLong >> 31;
    }

    private String deleteHightOnebit(byte paramByte)
    {
        return new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(new StringBuffer().append(paramByte >> 6 & 0x1).append("").toString()).append(paramByte >> 5 & 0x1).toString()).append("").toString()).append(paramByte >> 4 & 0x1).toString()).append("").toString()).append(paramByte >> 3 & 0x1).toString()).append("").toString()).append(paramByte >> 2 & 0x1).toString()).append("").toString()).append(paramByte >> 1 & 0x1).toString()).append("").toString() + (paramByte & 0x1);
    }

    private boolean hasMoreByte(byte paramByte)
    {
        return ((paramByte >> 7) & 1) == 1;

    }

    private byte[] read2End()
    {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        for (;;)
        {
            byte b = this.bb.readByte();
            if (!hasMoreByte(b))
            {
                localByteArrayOutputStream.write(b);
                return localByteArrayOutputStream.toByteArray();
            }
            localByteArrayOutputStream.write(b);
        }
    }

    public HeadData readHead()
    {
        HeadData localHeadData = new HeadData(this);
        long l = Byte2Long(read2End());
        localHeadData.type = ((int)(l & 7));
        localHeadData.tag = ((int)(l >> 3));
        return localHeadData;
    }

    private byte[] readLen(int paramInt)
    {
        byte[] arrayOfByte = new byte[paramInt];
        this.bb.readBytes(arrayOfByte);
        return arrayOfByte;
    }

    private void skip(int i)
    {
        this.bb.readerIndex(this.bb.readerIndex() + i);
    }

    private void skipField(int paramInt)
    {
        if (paramInt == 2)
        {
            skip((int)Byte2Long(read2End()));
            return;
        }
        for (;;)
        {
            if (!hasMoreByte(this.bb.readByte()))
            {
                break;
            }
        }
    }

    private boolean skipTag(final int n)
    {
        try
        {
            for (HeadData readHead = readHead(); readHead.tag != n; readHead = readHead())
            {
                skipField(readHead.type);
            }
            return true;
        }
        catch (BufferUnderflowException e)
        {
            //e.printStackTrace();
            return false;
        }
        catch(java.lang.IndexOutOfBoundsException e){
            //e.printStackTrace();
            return false;
        }
    }

    public static byte[] writeLengthDelimit(int paramInt, byte... paramVarArgs)
    {
        ByteBuf buf = Unpooled.directBuffer();
        writeVarint(buf, paramInt << 3 | 0x2);
        writeVarint(buf, paramVarArgs.length);
        buf.writeBytes(paramVarArgs);
        byte[] toByteArray = new byte[buf.readableBytes()];
        buf.readBytes(toByteArray);
        buf.release();
        return toByteArray;

//      byte[] arrayOfByte1 = writeVarint(paramVarArgs.length);
//      byte[] arrayOfByte2 = writeVarint(paramInt << 3 | 0x2);
//      byte[] arrayOfByte3 = new byte[paramVarArgs.length + arrayOfByte2.length + arrayOfByte1.length];
//      System.arraycopy(arrayOfByte2, 0, arrayOfByte3, 0, arrayOfByte2.length);
//      System.arraycopy(arrayOfByte1, 0, arrayOfByte3, arrayOfByte2.length, arrayOfByte1.length);
//      System.arraycopy(paramVarArgs, 0, arrayOfByte3, arrayOfByte1.length + arrayOfByte2.length, paramVarArgs.length);
//      return arrayOfByte3;
    }

    public static byte[] writeVarint(int paramInt, long paramLong)
    {
        ByteBuf buf = Unpooled.directBuffer();
        writeVarint(buf, paramInt << 3 | 0x0);
        writeVarint(buf, paramLong);
        byte[] toByteArray = new byte[buf.readableBytes()];
        buf.readBytes(toByteArray);
        buf.release();
        return toByteArray;
//      byte[] arrayOfByte2 = writeVarint(paramLong);
//      byte[] arrayOfByte1 = writeVarint(paramInt << 3 | 0x0);
//      byte[] arrayOfByte3 = new byte[arrayOfByte2.length + arrayOfByte1.length];
//      System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 0, arrayOfByte1.length);
//      System.arraycopy(arrayOfByte2, 0, arrayOfByte3, arrayOfByte1.length, arrayOfByte2.length);
//      return arrayOfByte3;
    }

    private static void writeVarint(ByteBuf buf, long j)
    {
        long abs = Math.abs(j);
        do {
            buf.writeByte((byte) ((int) ((127 & abs) | 128)));
            abs >>= 7;
        } while (abs != 0);
        buf.readerIndex(buf.readableBytes() - 1);
        byte y =buf.readByte();
        buf.readerIndex(0);
        buf.writerIndex(buf.readableBytes() - 1);
        buf.writeByte(y & 127);
    }

    public byte[] readLengthDelimited(int i)
    {
        int position = this.bb.readerIndex();
        if (this.skipTag(i))
        {
            byte[] data_to_return = this.readLen((int) Byte2Long(read2End()));
            return data_to_return;
        }
        this.bb.readerIndex(position);
        return null;
    }

    public long readVarint(int i)
    {
        return  skipTag(i) ? Byte2Long(read2End()): 0;
    }

    public String readString(int i)
    {
        byte[] h = this.readLengthDelimited(i);
        return (h!=null)?new String(h):"";
    }
}

