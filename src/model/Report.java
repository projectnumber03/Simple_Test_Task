package model;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Report {
    private ArrayList<Page> pages = new ArrayList<>();
    private ArrayList<String> settings = new ArrayList<>();
    private Page currentPage;

    public Report(String xmlName) throws FileNotFoundException {
        final String fileName = xmlName;
        try {
            XMLStreamReader xmlr = XMLInputFactory.newInstance().createXMLStreamReader(fileName, new FileInputStream(fileName));
            while (xmlr.hasNext()) {
                xmlr.next();
                if (xmlr.hasText() && xmlr.getText().trim().length() > 0) {
                    settings.add(xmlr.getText());
                }
            }
        } catch (XMLStreamException ex) {
            ex.printStackTrace();
        }
        currentPage = new Page(Integer.valueOf(settings.get(1)));
        currentPage.addHeader(settings);
        currentPage.addSeparator(settings);
    }

    public void fill(String data) {
        Line line = currentPage.newLine(settings, data);
        if(currentPage.getHeightCurrent() + line.getHeight() <= currentPage.getHeight()){
            currentPage.addLine(line);
            currentPage.addSeparator(settings);
        }else {
            pages.add(currentPage);
            currentPage = new Page(Integer.valueOf(settings.get(1)));
            currentPage.addHeader(settings);
            currentPage.addSeparator(settings);
            currentPage.addLine(line);
            currentPage.addSeparator(settings);
        }
    }

    public void print(){
        pages.add(currentPage);
        for (Page page : pages){
            page.print();
            if(pages.indexOf(page) != pages.size() - 1){
                System.out.println("~");
            }
        }
    }
}
