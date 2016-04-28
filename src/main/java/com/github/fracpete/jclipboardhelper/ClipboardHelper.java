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
 * ClipboardHelper.java
 * Copyright (C) 2016 University of Waikato, Hamilton, New Zealand
 */
package com.github.fracpete.jclipboardhelper;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JTable;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

/**
 * Helper class for dealing with the system clipboard.
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision: 4584 $
 */
public class ClipboardHelper {

  /**
   * Copies the given transferable to the system's clipboard.
   *
   * @param t		the transferable to copy
   */
  public static void copyToClipboard(Transferable t) {
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(t, null);
  }

  /**
   * Copies the given string to the system's clipboard.
   *
   * @param s		the string to copy
   */
  public static void copyToClipboard(String s) {
    copyToClipboard(new TransferableString(s));
  }

  /**
   * Copies the given image to the system's clipboard.
   *
   * @param img		the image to copy
   */
  public static void copyToClipboard(BufferedImage img) {
    copyToClipboard(new TransferableImage(img));
  }

  /**
   * Copies the given JComponent as image to the system's clipboard.
   *
   * @param comp		the component to copy
   */
  public static void copyToClipboard(JComponent comp) {
    BufferedImage img;
    Graphics g;

    img  = new BufferedImage(comp.getWidth(), comp.getHeight(), BufferedImage.TYPE_INT_RGB);
    g    = img.getGraphics();
    g.setPaintMode();
    g.fillRect(0, 0, comp.getWidth(), comp.getHeight());
    comp.printAll(g);
    copyToClipboard(img);
  }

  /**
   * Copies the given JTable as text to the system's clipboard.
   *
   * @param table		the table to copy
   */
  public static void copyToClipboard(JTable table) {
    Action copy;
    ActionEvent event;

    copy  = table.getActionMap().get("copy");
    event = new ActionEvent(table, ActionEvent.ACTION_PERFORMED, "");
    copy.actionPerformed(event);
  }

  /**
   * Checks whether the specified "flavor" can be obtained from the clipboard.
   *
   * @param flavor	the type of data to look for
   * @return		true if the data can be obtained, false if not available
   */
  public static boolean canPasteFromClipboard(DataFlavor flavor) {
    Clipboard clipboard;
    boolean		result;

    try {
      clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      result    = clipboard.isDataFlavorAvailable(flavor);
    }
    catch (Exception e) {
      result = false;
    }

    return result;
  }

  /**
   * Checks whether a string can be obtained from the clipboard.
   *
   * @return		true if string can be obtained, false if not available
   */
  public static boolean canPasteStringFromClipboard() {
    return canPasteFromClipboard(DataFlavor.stringFlavor);
  }

  /**
   * Obtains an object from the clipboard.
   *
   * @param flavor	the type of object to obtain
   * @return		the obtained object, null if not available
   */
  public static Object pasteFromClipboard(DataFlavor flavor) {
    Clipboard 		clipboard;
    Object		result;
    Transferable	content;

    result = null;

    try {
      clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      content   = clipboard.getContents(null);
      if ((content != null) && (content.isDataFlavorSupported(flavor)))
	result = content.getTransferData(flavor);
    }
    catch (Exception e) {
      result = null;
    }

    return result;
  }

  /**
   * Obtains a string from the clipboard.
   *
   * @return		the obtained string, null if not available
   */
  public static String pasteStringFromClipboard() {
    Clipboard 		clipboard;
    String		result;
    Transferable	content;

    result = null;

    try {
      clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      content   = clipboard.getContents(null);
      if ((content != null) && (content.isDataFlavorSupported(DataFlavor.stringFlavor)))
	result = (String) content.getTransferData(DataFlavor.stringFlavor);
    }
    catch (Exception e) {
      result = null;
    }

    return result;
  }

}
