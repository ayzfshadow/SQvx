package com.saki.qq.datapacket.unpack;

public class ReUnknown extends RecviePack
{

	public String cmd;
    public ReUnknown(byte[] bArr,String f) {
        super(bArr);
		this.cmd=f;
    }
}
