package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import aj210328.Router;

/**
 *
 * @author janko
 */
public class GraphMenu extends JFrame {

	private JPanel bottomPanel;
	private JComboBox<String> routerChoice;
	private JComboBox<String> mibChoice;
	public static final Font defaultFont = new Font("Segoe UI", Font.PLAIN, 30);
	private JPanel graphHolderPanel;
	private JPanel oneGraphPanel;
	private JPanel twoGraphPanel;
	
	private LineGraph lg1;
	private LineGraph lg2;

	public GraphMenu() {
		setSize(1400, 1200);
		setTitle("RM2_Projekat_janko");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		initComponents();
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	}

	public void updateGraphs() {
		switch (GraphController.getNumberOfGraphsNeeded()) {
		case 1:
			oneGraphPanel.removeAll();
			lg1 = new LineGraph(GraphController.extractSelectedDataOneGraph(),"",false);
			oneGraphPanel.add(lg1, BorderLayout.CENTER);

			graphHolderPanel.removeAll();
			graphHolderPanel.add(oneGraphPanel);
			oneGraphPanel.revalidate();
			break;
		case 2:
			twoGraphPanel.removeAll();
			ArrayList<ArrayList<Integer>> list = GraphController.extractSelectedDataTwoGraphs();
			lg1 = new LineGraph(list.get(0), Router.memPoolName1,true);
			lg2 = new LineGraph(list.get(1), Router.memPoolName2,true);
			twoGraphPanel.add(lg1);
			twoGraphPanel.add(lg2);

			graphHolderPanel.removeAll();
			graphHolderPanel.add(twoGraphPanel);
			twoGraphPanel.revalidate();
			break;
		default:
			System.err.println("Greska u updateGraphs(), nedozvoljen broj potrebnih grafova.");
			break;
		}
		graphHolderPanel.revalidate();
		graphHolderPanel.repaint();
	}

	private void initComponents() {
		initBottomPanel();
		getContentPane().add(bottomPanel, BorderLayout.PAGE_END);

		graphHolderPanel = new JPanel(new BorderLayout());
		oneGraphPanel = new JPanel(new BorderLayout());
		twoGraphPanel = new JPanel(new GridLayout(2, 1));
		getContentPane().add(graphHolderPanel, BorderLayout.CENTER);

		lg1 = new LineGraph(GraphController.extractSelectedDataOneGraph(),"",false);
		lg2 = new LineGraph(GraphController.extractSelectedDataOneGraph(),"",false);
				
		oneGraphPanel.add(lg1, BorderLayout.CENTER);

		twoGraphPanel.add(lg1);
		twoGraphPanel.add(lg2);

		updateGraphs();
	}

	private void initBottomPanel() {
		bottomPanel = new JPanel(new GridBagLayout());
		bottomPanel.setBackground(Color.yellow);
		routerChoice = new JComboBox<>();
		mibChoice = new JComboBox<>();

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		//biranje rutera koji se prikazuje
		routerChoice.addItem("Ruter 1(192.168.10.1)");
		routerChoice.addItem("Ruter 2(192.168.20.1)");
		routerChoice.addItem("Ruter 3(192.168.30.1)");
		routerChoice.setFont(defaultFont);
		routerChoice.addActionListener((e) -> {
			// System.out.println(routerChoice.getSelectedItem());
			switch (routerChoice.getSelectedItem().toString()) {
			case "Ruter 1(192.168.10.1)":
				// RouterRegister.setSelectedRouter(1);
				GraphController.setSelectedRouter(1);
				break;
			case "Ruter 2(192.168.20.1)":
				// RouterRegister.setSelectedRouter(2);
				GraphController.setSelectedRouter(2);
				break;
			case "Ruter 3(192.168.30.1)":
				// RouterRegister.setSelectedRouter(3);
				GraphController.setSelectedRouter(3);
				break;
			default:
				break;
			}
			updateGraphs();
		});
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		bottomPanel.add(routerChoice, gbc);

		//biranje parametra koji se prikazuje
		mibChoice.addItem("CPU usage last 5s");
		mibChoice.addItem("CPU usage last 1min");
		mibChoice.addItem("CPU usage last 5min");
		mibChoice.addItem("Amount of used memory");
		mibChoice.addItem("Amount of free memory");
		mibChoice.setFont(defaultFont);
		mibChoice.addActionListener((e) -> {
			// System.out.println(mibChoice.getSelectedItem());
			GraphController.setSelectedMIB(mibChoice.getSelectedItem().toString());
			updateGraphs();
		});
		gbc.gridx = 1;
		gbc.gridy = 0;
		bottomPanel.add(mibChoice, gbc);
	}
}
