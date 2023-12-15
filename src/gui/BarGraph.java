package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class BarGraph extends JPanel {

	private ArrayList<Integer> data;

	private int w = 1400;
	private int h = 500;

	public BarGraph(ArrayList<Integer> d) {
		data = d;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (data.isEmpty())
			return;
		w = getWidth();
		h = getHeight();

		int barWidth = w / data.size();
		int maxValue = getMaxValue();
		if (maxValue < 1)
			maxValue = 1;

		for (int i = 0; i < data.size(); i++) {
			int barHeight = (int) ((double) data.get(i) / maxValue * (h-50));

			int x = i * barWidth;
			int y = h - barHeight;

			g.setColor(Color.blue);
			g.fillRect(x, y, barWidth, barHeight);

			g.setColor(Color.black);
			g.drawRect(x, y, barWidth, barHeight);

			// Display data values above the bars
			//g.setFont(GraphMenu.defaultFont);
			g.drawString(Integer.toString(data.get(i)), x + barWidth / 2, y - 5);
		}
	}

	private int getMaxValue() {
		int maxValue = Integer.MIN_VALUE;
		for (int value : data) {
			if (value > maxValue) {
				maxValue = value;
			}
		}
		return maxValue;
	}

	public void setData(ArrayList<Integer> data) {
		this.data = data;
	}
	
	
}