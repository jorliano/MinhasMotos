package br.com.jortec.minhasmotos.dominio;

/**
 * Created by Jorliano on 06/01/2016.
 */
public class ContextMenuItem {
    private int icon;
    private String label;

    public ContextMenuItem(int icon, String label){
        this.setIcon(icon);
        this.setLabel(label);
    }


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
