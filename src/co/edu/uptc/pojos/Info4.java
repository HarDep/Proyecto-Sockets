package co.edu.uptc.pojos;

public class Info4 {
    private FigureInformation2 figureInformation;
    private PanelInformation panelInformation;
    private FrameInformation frameInformation;

    public Info4(FigureInformation2 figureInformation, PanelInformation panelInformation, FrameInformation frameInformation) {
        this.figureInformation = figureInformation;
        this.panelInformation = panelInformation;
        this.frameInformation = frameInformation;
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

    public FrameInformation getFrameInformation() {
        return frameInformation;
    }

    public void setFrameInformation(FrameInformation frameInformation) {
        this.frameInformation = frameInformation;
    }
}
