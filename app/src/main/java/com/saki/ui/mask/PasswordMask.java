package com.saki.ui.mask;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

public class PasswordMask extends PasswordTransformationMethod {

    private class a implements CharSequence {
        char[] a = {9312, 9313, 9314, 9315, 9316, 9317, 9318, 9319, 9320, 9321, 9322, 9323, 9324, 9325, 9326, 9327, 9328, 9329, 9330, 9331};
        private CharSequence c;

        public a(CharSequence charSequence) {
            this.c = charSequence;
        }

        public char charAt(int i) {
            return this.a[i % this.a.length];
        }

        public int length() {
            return this.c.length();
        }

        public CharSequence subSequence(int i, int i2) {
            return this.c.subSequence(i, i2);
        }
    }

    public CharSequence getTransformation(CharSequence charSequence, View view) {
        return new a(charSequence);
    }
}
