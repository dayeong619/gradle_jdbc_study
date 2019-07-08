package gradle_jdbc_study.ui.content;

import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public abstract class AbstractPanelContent<T> extends JPanel {
	public abstract void setItem(T dept);
	public abstract T getItem();
	public abstract void clearComponent(int nextNo);
	public abstract JTextField getTfNo();
	public abstract void setComponentAllEditable(boolean isEditable);
}







