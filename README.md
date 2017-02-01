# jclipboardhelper #

Helper library for dealing with the sytem's clipboard in Java.

## Maven ##

You can use the following dependency in your `pom.xml`:
```xml
<dependency>
  <groupId>com.github.fracpete</groupId>
  <artifactId>jclipboardhelper</artifactId>
  <version>0.1.1</version>
</dependency>
```

## Supported formats ##

* Copy to clipboard

  * `java.lang.String`
  * `java.awt.image.BufferedImage`
  * `javax.swing.JComponent` -- creates images from it
  * `javax.swing.JTable` -- call the table's *copy* action
  * `java.awt.datatransfer.Transferable` -- supply your own implementation

* Paste from clipboard

  * `java.lang.String`
  * `java.awt.image.BufferedImage`
  * `java.awt.datatransfer.Transferable` -- returns just an `Object`


## Example usage ##

Example code for copying data to the clipboard:

```java
import java.awt.image.BufferedImage;
import com.github.fracpete.jclipboardhelper.ClipboardHelper;
...
// string
ClipboardHelper.copyToClipboard("Hello World");
// image
BufferedImage img = ...  // from somewhere
ClipboardHelper.copyToClipboard(img);
```

Example code for obtaining data from the clipboard:

```java
import java.awt.image.BufferedImage;
import com.github.fracpete.jclipboardhelper.ClipboardHelper;
...
// string available?
if (ClipboardHelper.canPasteStringFromClipboard()) {
  String s = ClipboardHelper.pasteStringFromClipboard();
}
// image available?
if (ClipboardHelper.canPasteImageFromClipboard()) {
  BufferedImage img = ClipboardHelper.pasteImageFromClipboard();
}
```
