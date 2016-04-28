/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * StringExample.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

package com.github.fracpete.jclipboardhelper.examples;

import com.github.fracpete.jclipboardhelper.ClipboardHelper;

/**
 * Shows how to use the library with strings.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class StringExample {

  public static void main(String args[]) {
    if (!ClipboardHelper.canPasteStringFromClipboard()) {
      System.out.println("No string on the clipboard, we'll just add one ourselves!");
      ClipboardHelper.copyToClipboard("Hello jclipboardhelper!");
      System.out.println(ClipboardHelper.pasteStringFromClipboard());
    }
    else {
      System.out.println("This is what we found on the clipboard:");
      System.out.println(ClipboardHelper.pasteStringFromClipboard());
    }
  }
}
