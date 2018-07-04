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

/*
 * ClipboardHelper.java
 * Copyright (C) 2016-2018 University of Waikato, Hamilton, New Zealand
 */
package com.github.fracpete.jclipboardhelper;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JTable;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

/**
 * Helper class for dealing with the system clipboard.
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 */
public class ClipboardHelper {

  /** instance of toolkit. */
  protected static Toolkit m_Toolkit;

  /** instance of clipboard. */
  protected static Clipboard m_Clipboard;

  /**
   * Returns the toolkit instance.
   *
   * @return		the instance
   */
  public static synchronized Toolkit getToolkit() {
    if (m_Toolkit == null)
      m_Toolkit = Toolkit.getDefaultToolkit();
    return m_Toolkit;
  }

  /**
   * Returns the system clipboard instance.
   *
   * @return		the instance
   */
  public static synchronized Clipboard getSystemClipboard() {
    if (m_Clipboard == null)
      m_Clipboard = getToolkit().getSystemClipboard();
    return m_Clipboard;
  }

  /**
   * Copies the given transferable to the system's clipboard.
   *
   * @param t		the transferable to copy
   */
  public static void copyToClipboard(Transferable t) {
    getSystemClipboard().setContents(t, null);
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
   * @param comp	the component to copy
   * @see		BufferedImage#TYPE_INT_RGB
   */
  public static void copyToClipboard(JComponent comp) {
    BufferedImage img;
    Graphics g;

    img  = new BufferedImage(comp.getWidth(), comp.getHeight(), BufferedImage.TYPE_INT_RGB);
    g    = img.createGraphics();
    g.setPaintMode();
    g.fillRect(0, 0, comp.getWidth(), comp.getHeight());
    comp.printAll(g);
    copyToClipboard(img);
    g.dispose();
  }

  /**
   * Copies the given JTable as text to the system's clipboard.
   *
   * @param table	the table to copy
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
    boolean		result;

    try {
      result = getSystemClipboard().isDataFlavorAvailable(flavor);
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
   * Checks whether a string can be obtained from the clipboard.
   *
   * @return		true if string can be obtained, false if not available
   */
  public static boolean canPasteImageFromClipboard() {
    return canPasteFromClipboard(DataFlavor.imageFlavor);
  }

  /**
   * Obtains an object from the clipboard.
   *
   * @param flavor	the type of object to obtain
   * @return		the obtained object, null if not available
   */
  public static Object pasteFromClipboard(DataFlavor flavor) {
    Object		result;
    Transferable	content;

    result = null;

    try {
      content = getSystemClipboard().getContents(null);
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
    String		result;
    Transferable	content;

    result = null;

    try {
      content = getSystemClipboard().getContents(null);
      if ((content != null) && (content.isDataFlavorSupported(DataFlavor.stringFlavor)))
        result = (String) content.getTransferData(DataFlavor.stringFlavor);
    }
    catch (Exception e) {
      result = null;
    }

    return result;
  }

  /**
   * Obtains a BufferedImage from the clipboard.
   * <br>
   * Taken from:
   * http://rsbweb.nih.gov/ij/developer/source/ij/plugin/Clipboard.java.html
   *
   * @return		the obtained image, null if not available
   * @see		BufferedImage#TYPE_INT_RGB
   */
  public static BufferedImage pasteImageFromClipboard() {
    BufferedImage	result;
    Image 		img;
    int 		width;
    int 		height;
    Graphics 		g;

    result = null;
    img    = (Image) pasteFromClipboard(DataFlavor.imageFlavor);
    if (img != null) {
      width  = img.getWidth(null);
      height = img.getHeight(null);
      result = new BufferedImage(width, height, java.awt.image.BufferedImage.TYPE_INT_RGB);
      g      = result.createGraphics();
      g.drawImage(img, 0, 0, null);
      g.dispose();
    }

    return result;
  }

  /**
   * For clearing the clipboard.
   * <br>
   * Taken from here: https://stackoverflow.com/a/18254944/4698227
   */
  public static void clearClipboard() {
    getSystemClipboard().setContents(new Transferable() {
      public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[0];
      }
      public boolean isDataFlavorSupported(DataFlavor flavor) {
        return false;
      }
      public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        throw new UnsupportedFlavorException(flavor);
      }
    }, null);
  }
}