package co.edu.uptc.pojos;

public class Info2 {
    private FigureInformation2 figureInformation;
    private PanelInformation panelInformation;

    public Info2(FigureInformation2 figureInformation, PanelInformation panelInformation) {
        this.figureInformation = figureInformation;
        this.panelInformation = panelInformation;
    }

    public FigureInformation2 getFigureInformation() {
        return figureInformation;
    }

    public void setFigureInformation(FigureInformation2 figureInformation) {
        this.figureInformation = figureInformation;
    }

    public PanelInformation getPanelInformation() {
        return panelInformation;
    }

    public void setPanelInformation(PanelInformation panelInformation) {
        this.panelInformation = panelInformation;
    }
}
