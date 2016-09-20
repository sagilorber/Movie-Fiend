/*
 * Copyright (c) 2016 PayPal, Inc.
 *
 * All rights reserved.
 *
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY
 * KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
 * PARTICULAR PURPOSE.
 */

package com.example.slorber.moviefiend;

import android.widget.TextView;

/**
 * TODO: Write Javadoc for AnimatedTextView.
 *
 * @author slorber
 */
public class AnimatedTextView {
    private final TextView textView;

    public AnimatedTextView(TextView textView) {this.textView = textView;}
    public String getText() {return textView.getText().toString();}
    public void setText(String text) {textView.setText(text);}
}
