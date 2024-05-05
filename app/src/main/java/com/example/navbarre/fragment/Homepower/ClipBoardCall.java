package com.example.navbarre.fragment.Homepower;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

public class ClipBoardCall {
    private Context context;

    public ClipBoardCall(Context context) {
        this.context = context;
    }

    public void copyTextToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
    }
}
