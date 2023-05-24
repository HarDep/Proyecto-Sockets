package co.edu.uptc.pojos;

public class Info {
    private FigureInformation figureInformation;
    private PanelInformation panelInformation;

    public Info(FigureInformation figureInformation, PanelInformation panelInformation) {
        this.figureInformation = figureInformation;
        this.panelInformation = panelInformation;
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
