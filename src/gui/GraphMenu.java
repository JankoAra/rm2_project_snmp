package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import aj210328.RouterRegister;

/**
 *
 * @author janko
 */
public class GraphMenu extends JFrame {

	private JPanel bottomPanel;
	private BarGraph barGraph;
	private JComboBox<String> routerChoice;
	private JComboBox<String> mibChoice;
	public static final Font defaultFont = new Font("Segoe UI", Font.PLAIN, 30);
	private JPanel graphPanel;

	public GraphMenu() {
		setSize(1400, 1200);
		setTitle("RM2_Projekat_janko");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		initComponents();
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

	}

	public void setBarGraph(BarGraph g) {
		barGraph = g;
	}

	public void updateBarGraph() {
//		BarGraph bg = BarGraphController.makeNewBarGraph();
//		barGraph = bg;
//		revalidate();
//		bg.repaint();
//		repaint();

		barGraph.setData(BarGraphController.extractSelectedData());
		barGraph.repaint();
		revalidate();
	}

	private void initComponents() {
		barGraph = BarGraphController.makeNewBarGraph();
		bottomPanel = new JPanel(new GridBagLayout());
		bottomPanel.setBackground(Color.red);
		routerChoice = new JComboBox<>();
		mibChoice = new JComboBox<>();

		getContentPane().add(barGraph, BorderLayout.CENTER);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		routerChoice.addItem("Ruter 1(192.168.10.1)");
		routerChoice.addItem("Ruter 2(192.168.20.1)");
		routerChoice.addItem("Ruter 3(192.168.30.1)");
		routerChoice.setFont(defaultFont);
		// routerChoice.addItemListener(e -> System.out.println(e.getItem()));
		routerChoice.addActionListener((e) -> {
			// System.out.println(routerChoice.getSelectedItem());
			switch (routerChoice.getSelectedItem().toString()) {
			case "Ruter 1(192.168.10.1)":
				// RouterRegister.setSelectedRouter(1);
				BarGraphController.setSelectedRouter(1);
				break;
			case "Ruter 2(192.168.20.1)":
				// RouterRegister.setSelectedRouter(2);
				BarGraphController.setSelectedRouter(2);
				break;
			case "Ruter 3(192.168.30.1)":
				// RouterRegister.setSelectedRouter(3);
				BarGraphController.setSelectedRouter(3);
				break;
			default:
				break;
			}
			updateBarGraph();
		});
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		bottomPanel.add(routerChoice, gbc);

		mibChoice.addItem("CPU usage last 5s");
		mibChoice.addItem("CPU usage last 1min");
		mibChoice.addItem("CPU usage last 5min");
		mibChoice.addItem("Amount of used memory");
		mibChoice.addItem("Amount of free memory");
		mibChoice.setFont(defaultFont);
		mibChoice.addActionListener((e) -> {
			// System.out.println(mibChoice.getSelectedItem());
			BarGraphController.setSelectedMIB(mibChoice.getSelectedItem().toString());
			updateBarGraph();
		});
		gbc.gridx = 1;
		gbc.gridy = 0;
		bottomPanel.add(mibChoice, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		JButton resetBtn = new JButton("Reset current graph");
		resetBtn.addActionListener(e->{
			BarGraphController.extractSelectedData().clear();
			updateBarGraph();
		});
		resetBtn.setFont(defaultFont);
		bottomPanel.add(resetBtn, gbc);

		getContentPane().add(bottomPanel, BorderLayout.PAGE_END);
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(() -> new GraphMenu().setVisible(true));
	}
}
