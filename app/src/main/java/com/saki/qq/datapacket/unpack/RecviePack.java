package com.saki.qq.datapacket.unpack;

import com.saki.util.Zip;

public abstract class RecviePack {
    private byte[] a;
    protected byte[] k;
    public Info l;

    public RecviePack(byte[] bArr) {
        this.k = bArr;
    }

    public RecviePack setInfo(Info classa) {
        this.l = classa;
        return this;
    }

    /* access modifiers changed from: protected */
    public byte[] checkZip(byte[] bArr) {
        if (bArr.length < 2) {
            return bArr;
        }
        if (bArr[0] == 120 && bArr[1] == -38) {
            return Zip.Inflater(bArr);
        }
        if (bArr[0] != 1 || bArr[1] != 120) {
            return bArr;
        }
        byte[] bArr2 = new byte[(bArr.length - 1)];
        System.arraycopy(bArr, 1, bArr2, 0, bArr2.length);
        return Zip.Inflater(bArr2);
    }

    public RecviePack setData(byte[] bArr) {
        this.a = bArr;
        return this;
    }

    public byte[] getData() {
        return this.a;
    }
}
