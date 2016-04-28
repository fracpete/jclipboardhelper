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
 * BufferedImageExample.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

package com.github.fracpete.jclipboardhelper.examples;

import com.github.fracpete.jclipboardhelper.ClipboardHelper;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Shows how to use the library with strings.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class BufferedImageExample {

  public static void main(String args[]) throws Exception {
    if (!ClipboardHelper.canPasteImageFromClipboard()) {
      System.out.println("No image on the clipboard, we'll just add one ourselves!");
      BufferedImage img = new BufferedImage(400, 300, BufferedImage.TYPE_INT_RGB);
      Graphics2D g = img.createGraphics();
      g.drawString("Hello jclipboardhelper!", 50, 150);
      g.dispose();
      ClipboardHelper.copyToClipboard(img);
      System.out.println("Try pasting into a word processor and press enter after pasting to end program.");
      System.in.read();
    }
    else {
      BufferedImage img = ClipboardHelper.pasteImageFromClipboard();
      if (img == null) {
        System.err.println("Failed to obtain image from clipboard, unfortunately.");
      }
      else {
        File outfile = new File(
          System.getProperty("java.io.tmpdir") + File.separator + "jclipboardhelper" + System.currentTimeMillis() + ".png");
        ImageIO.write(img, "png", outfile);
	System.out.println("The image that we found on the clipboard has been saved as:");
	System.out.println(outfile);
      }
    }
  }
}
