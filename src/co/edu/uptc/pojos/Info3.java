package co.edu.uptc.pojos;

public class Info3 {
    private FigureInformation figureInformation;
    private PanelInformation panelInformation;
    private FileReading fileReading;

    public Info3(FigureInformation figureInformation, PanelInformation panelInformation, FileReading fileReading) {
        this.figureInformation = figureInformation;
        this.panelInformation = panelInformation;
        this.fileReading = fileReading;
    }

    public FileReading getFileReading() {
        return fileReading;
    }

    public void setFileReading(FileReading fileReading) {
        this.fileReading = fileReading;
    }

    public FigureInformation getFigureInformation() {
        return figureInformation;
    }

    public void setFigureInformation(FigureInformation figureInformation) {
        this.figureInformation = figureInformation;
    }

    public PanelInformation getPanelInformation() {
        return panelInformation;
    }

    public void setPanelInformation(PanelInformation panelInformation) {
        this.panelInformation = panelInformation;
    }
}
