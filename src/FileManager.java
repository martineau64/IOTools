package ioTools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import structures.CustomTree;

public class FileManager {

    private String rootLabel = "%root%";
    
    private enum TokenTag {
        OPEN, CLOSE, BLANK, OTHER
    }
    
    private List<String> getFileLineByLine(String filePathName, boolean keepBlankLines)
    throws IOException {
        try {
			List<String> lines = new ArrayList<String>();
            BufferedReader reader = new BufferedReader(new FileReader(filePathName));
			String line = reader.readLine();
			while (line != null) {
                if (keepBlankLines || !line.isBlank()) {
                    lines.add(line);
                }
				line = reader.readLine();
			}
			reader.close();
            return lines;
		} catch (IOException e) {
			throw e;
		}
    }

    public List<String> splitXMLLine(String line)
    throws IOException {
        List<String> XMLparts = new ArrayList<String>();
        String subLine = line;
        int startOfTag;
        int endOfTag;
        while (!subLine.isBlank()) {
            startOfTag = subLine.indexOf("<");
            endOfTag = 0;
            if (startOfTag < 0) {
                throw new IOException("Invalid XML line: Missing StartofTag < in:\n" + line);
            } else {
                endOfTag = subLine.indexOf(">");
                if (endOfTag < 0) {
                    throw new IOException("Invalid XML line: Missing EndOfTag > in:\n" + line);
                } else {
                    XMLparts.add(subLine.substring(0, startOfTag));
                    XMLparts.add(subLine.substring(startOfTag, endOfTag + 1));
                    subLine = subLine.substring(endOfTag + 1);
                }
            }
        }
        return XMLparts;
    }

    public boolean parseToken(String token, TokenTag expected) {
        int n = token.length();
        boolean result = true;
        switch (expected) {
            case OPEN:
                // check for <*>
                if (n < 2) {
                    return false;
                }
                result &= token.indexOf("<") == 0;
                result &= token.indexOf("/") != 1;
                result &= token.indexOf(">") + 1 == n;
                result &= token.substring(1).indexOf("<") < 0;
                return result;
            case CLOSE:
                // check for </*>
                if (n < 3) {
                    return false;
                }
                result &= token.indexOf("</") == 0;
                result &= token.indexOf(">") + 1 == n;
                result &= token.substring(1).indexOf("<") < 0;
                return result;
            case BLANK:
                return token.isBlank();
            default:
                return result;
        }
    }
    
    public CustomTree<String> parseLine(String line, List<String> XMLparts, CustomTree<String> parent)
    throws IOException {
        int n = XMLparts.size();
        switch (n) {
            case 2:
                if (!this.parseToken(XMLparts.get(0), TokenTag.BLANK)) {
                    throw new IOException("Invalid XML line: Wrong token configuration in\n" + line);
                } else {
                    if (this.parseToken(XMLparts.get(1), TokenTag.OPEN)) {
                        String openTag = XMLparts.get(1);
                        int lnToken = openTag.length();
                        String childLabel = XMLparts.get(1).substring(1, lnToken-1);
                        CustomTree<String> child = parent.addChild(childLabel);
                        return child;
                    } else {
                        if (this.parseToken(XMLparts.get(1), TokenTag.CLOSE)) {
                            String closeTag = XMLparts.get(1);
                            int lnToken = closeTag.length();
                            String closeLabel = XMLparts.get(1).substring(2, lnToken-1);
                            if (closeLabel.equals(parent.getLabel()) && !closeLabel.equals(this.rootLabel)) {
                                return parent.getParent();
                            } else {
                                throw new IOException("Invalid XML line: Unexisting label " + closeLabel + ". Should close "
                                + parent.getLabel() + "in\n" + line);
                            }
                        } else {
                            throw new IOException("Invalid XML line: Wrong token configuration in\n" + line);
                        }
                    }
                }
            case 4:
                boolean valid = true;
                valid &= this.parseToken(XMLparts.get(0), TokenTag.BLANK);
                valid &= this.parseToken(XMLparts.get(1), TokenTag.OPEN);
                valid &= this.parseToken(XMLparts.get(2), TokenTag.OTHER);
                valid &= this.parseToken(XMLparts.get(3), TokenTag.CLOSE);
                if (!valid) {
                    throw new IOException("Invalid XML line: Wrong token configuration in\n" + line);
                } else {
                    String openTag = XMLparts.get(1);
                    int lnToken = openTag.length();
                    String childLabel = XMLparts.get(1).substring(1, lnToken-1);
                    CustomTree<String> child = parent.addChild(childLabel);
                    child.addChild(XMLparts.get(2));
                    return parent;
                }
            default:
                throw new IOException("Invalid XML line: Wrong token configuration in\n" + line);
        }
    }
    
    public CustomTree<String> readXMLFile(String filePathName)
    throws IOException {
		List<String> lines = this.getFileLineByLine(filePathName, false);
        if (lines.size() == 0) {
            throw new IOException("File " + filePathName + ":\n Empty or blank file, doesn't match standard xml format.");
        } else {
            CustomTree<String> parent = new CustomTree<String>(this.rootLabel);
            for (String line : lines) {
                List<String> splittedLine = this.splitXMLLine(line);
                parent = this.parseLine(line, splittedLine, parent);
            }
            if (parent.getLabel().equals(this.rootLabel)) {
                return parent;
            } else {
                throw new IOException("Invalid XML file: All XML tags haven't been closed\n");
            }
        }
    }
}
