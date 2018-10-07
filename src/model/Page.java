package model;

import java.util.ArrayList;

public class Page {
    private ArrayList<Line> lines = new ArrayList<>();
    private int heightCurrent;
    private int height;

    public Page(int height) {
        this.height = height;
        this.heightCurrent = 0;
    }

    public int getHeightCurrent() {
        return heightCurrent;
    }

    public int getHeight() {
        return height;
    }

    public Line newLine(ArrayList<String> settings, String data){
        String[] dataSplit = data.split("\t");
        return new Line(Integer.valueOf(settings.get(3)), Integer.valueOf(settings.get(5)), Integer.valueOf(settings.get(7)), dataSplit[0], dataSplit[1], dataSplit[2]);
    }

    public void addLine(Line line){
        lines.add(line);
        heightCurrent += line.getHeight();
    }

    public void addHeader(ArrayList<String> settings){
        Header header = new Header(Integer.valueOf(settings.get(3)), Integer.valueOf(settings.get(5)), Integer.valueOf(settings.get(7)), settings.get(2), settings.get(4), settings.get(6));
        lines.add(header);
        heightCurrent += header.getHeight();
    }

    public void addSeparator(ArrayList<String> settings){
        Separator separator = new Separator(Integer.valueOf(settings.get(0)));
        lines.add(separator);
        heightCurrent += separator.getHeight();
    }

    public void print(){
        lines.remove(lines.size() - 1);
        for (Line line : lines){
            line.print();
        }
    }
}
