
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JCheckBoxMenuItem;
import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main extends JFrame implements Observer {

    private JTextField addTextField;
    private DownloadsTableModel tableModel;
    private JTable table;
    private JButton pauseButton, resumeButton;
    private JButton cancelButton, clearButton;
    private JButton open;
    private Download selectedDownload;
    private boolean clearing;
    private JFrame test;
    private JFrame AddUrl;

    public Main() {
        setTitle("A-Z Download Manager");
        //ImageIcon img = new ImageIcon("icon02.PNG"); //Tämä on kuvake(icon). 
        //setIconImage(img.getImage());
        // setIconImage(new ImageIcon("icon02.png").getImage());
        setSize(640, 480);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                actionExit();
            }
        });

        JMenuBar menubar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem fileAddDMenuItem = new JMenuItem("Add Url");
        fileAddDMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        fileAddDMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionAddurl();

            }
        });
        fileMenu.add(fileAddDMenuItem);
        //////////Open Folder//////////////
        JMenuItem fileopenfolderMenuItem = new JMenuItem("Open Folder", KeyEvent.VK_X);
        fileopenfolderMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        fileopenfolderMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionopenfolder();
            }
        });
        fileMenu.add(fileopenfolderMenuItem);

        //////////Exit Submenu///////////
        JMenuItem fileExitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        fileExitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        fileExitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionExit();
            }
        });
        fileMenu.add(fileExitMenuItem);
        menubar.add(fileMenu);
        setJMenuBar(menubar);

        ///////////View Menu///////////////
        JMenu ViewMenu = new JMenu("View");
        ViewMenu.setMnemonic(KeyEvent.VK_F);

        // JCheckBoxMenuItem ViewPercentsMenuItem = new JMenuItem("Percent", KeyEvent.VK_X);
        //ViewPercentsMenuItem.addActionListener(new ActionListener() {
        //  public void actionPerformed(ActionEvent e) {
        //    actionAddD();
        //}
        //});
        JCheckBoxMenuItem ViewURLMenuItem = new JCheckBoxMenuItem("URL");
        ViewURLMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
//ViewURLMenuItem.addItemListener((ItemEvent e)->{
        //  if (e.getStateChange() == ItemEvent.SELECTED) {
        //    DownloadsTableModel.getColumnName().setVisible(true);
        //} else {
        //  DownloadsTableModel.setVisible(false);
        //}
//});
//ViewPercentsMenuItem.setDisplayedMnemonicIndex(5);
        ViewMenu.add(ViewURLMenuItem);
        JCheckBoxMenuItem ViewSizeMenuItem = new JCheckBoxMenuItem("Size");
        ViewSizeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        ViewMenu.add(ViewSizeMenuItem);

        JCheckBoxMenuItem ViewProgressMenuItem = new JCheckBoxMenuItem("Progress");
        ViewProgressMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        ViewMenu.add(ViewProgressMenuItem);

        JCheckBoxMenuItem ViewStatusMenuItem = new JCheckBoxMenuItem("Status");
        ViewStatusMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        ViewMenu.add(ViewStatusMenuItem);

        menubar.add(ViewMenu);
        setJMenuBar(menubar);
        //////Tools menu///////////////
        JMenu ToolsMenu = new JMenu("Tools");
        ToolsMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem ToolsPauseMenuItem = new JMenuItem("Pause", KeyEvent.VK_X);
        ToolsPauseMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        ToolsPauseMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPause();
            }
        });
        ToolsMenu.add(ToolsPauseMenuItem);

        JMenuItem ToolsResumeMenuItem = new JMenuItem("Resume", KeyEvent.VK_X);
        ToolsResumeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        ToolsResumeMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionResume();
            }
        });
        ToolsMenu.add(ToolsResumeMenuItem);

        JMenuItem ToolsCancelMenuItem = new JMenuItem("Cancel", KeyEvent.VK_C);
        ToolsCancelMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        ToolsCancelMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionCancel();
            }
        });
        ToolsMenu.add(ToolsCancelMenuItem);

        JMenuItem ToolsClearMenuItem = new JMenuItem("Remove", KeyEvent.VK_D);
        ToolsClearMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        ToolsClearMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionClear();
            }
        });
        ToolsMenu.add(ToolsClearMenuItem);

        menubar.add(ToolsMenu);
        setJMenuBar(menubar);
        /////Help Menu//////
        final JMenu HelpMenu = new JMenu("Help");
        HelpMenu.setMnemonic(KeyEvent.VK_F);
        JMenuItem HelpAboutMenuItem = new JMenuItem("About");
        HelpAboutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        HelpAboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Help().setVisible(true);
            }
        });
        HelpMenu.add(HelpAboutMenuItem);
        //new test().setVisible(true);
        menubar.add(HelpMenu);
        setJMenuBar(menubar);

        //Add Puause Rsume Cancel Clear Panel button//
        JPanel addPanel = new JPanel();

        JButton addButton = new JButton("Add URL");
        addButton.setPreferredSize(new Dimension(200, 30));

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionAddurl();
            }
        });
        //addButton.setBackground(Color.RGB(57,217,141));
        addButton.setBackground(new java.awt.Color(47, 217, 141));
        addPanel.add(addButton);

        pauseButton = new JButton("Pause");
        pauseButton.setPreferredSize(new Dimension(90, 30));
        pauseButton.setBackground(new java.awt.Color(47, 217, 141));
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPause();
            }
        });
        pauseButton.setEnabled(false);
        addPanel.add(pauseButton);

        resumeButton = new JButton("Resume");
        resumeButton.setPreferredSize(new Dimension(90, 30));
        resumeButton.setBackground(new java.awt.Color(47, 217, 141));
        resumeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionResume();
            }
        });
        resumeButton.setEnabled(false);
        addPanel.add(resumeButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(90, 30));
        cancelButton.setBackground(new java.awt.Color(47, 217, 141));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionCancel();
            }
        });
        cancelButton.setEnabled(false);
        addPanel.add(cancelButton);

        clearButton = new JButton("Remove");
        clearButton.setPreferredSize(new Dimension(90, 30));
        clearButton.setBackground(new java.awt.Color(47, 217, 141));
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionClear();
            }
        });
        clearButton.setEnabled(false);
        addPanel.add(clearButton);

        //Download Table
        tableModel = new DownloadsTableModel();
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                tableSelectionChanged();
            }
        });
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ProgressRenderer renderer = new ProgressRenderer(0, 100);
        renderer.setStringPainted(true);
        table.setDefaultRenderer(JProgressBar.class, renderer);
        table.setRowHeight((int) renderer.getPreferredSize().getHeight());

        JPanel downloadPanel = new JPanel();
        downloadPanel.setBorder(BorderFactory.createTitledBorder("Downloads"));
        downloadPanel.setLayout(new BorderLayout());
        downloadPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton quitButton = new JButton("Quit");
        quitButton.setPreferredSize(new Dimension(90, 30));
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionExit();
            }
        });
        buttonPanel.add(quitButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(addPanel, BorderLayout.NORTH);
        getContentPane().add(downloadPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
    /////////////////Exit Submenu///////////////////////////

    private void actionExit() {
        System.exit(0);
    }

    ///////////////////////////Add Submenu/////////////////////////////////
    private void actionAddD() {
        JPanel addPanel = new JPanel();
        addTextField = new JTextField(10);
        addPanel.add(addTextField);

        JButton addButton = new JButton("Add Download");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionAdd();
            }
        });
        addPanel.add(addButton);

        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPause();
            }
        });
        pauseButton.setEnabled(false);
        addPanel.add(pauseButton);

        resumeButton = new JButton("Resume");
        resumeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionResume();
            }
        });
        resumeButton.setEnabled(false);
        addPanel.add(resumeButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionCancel();
            }
        });
        cancelButton.setEnabled(false);
        addPanel.add(cancelButton);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionClear();
            }
        });
        clearButton.setEnabled(false);
        addPanel.add(clearButton);
    }
    /////////////openfolder submenu//////////////////

    private void actionopenfolder() {

        JPanel addPanel = new JPanel();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        }

    }

    public void actionAddurl() {
        final JFrame frame = new JFrame("A_Z_Download Manager");

        frame.setSize(640, 120);

        JPanel addPanel = new JPanel();
        addTextField = new JTextField(40);
        addPanel.add(addTextField);
        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(90, 30));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionAdd();
                frame.dispose();
            }
        });
        addPanel.add(okButton);

        JButton ExitButton = new JButton("Exit");
        ExitButton.setPreferredSize(new Dimension(90, 30));
        ExitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        addPanel.add(ExitButton);

        frame.getContentPane().add(addPanel, BorderLayout.CENTER);
        frame.setVisible(true);

    }

    public void actionAdd() {
        URL verifiedUrl = verifyUrl(addTextField.getText());
        if (verifiedUrl != null) {
            tableModel.addDownload(new Download(verifiedUrl));
            addTextField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Download Url", "Error", JOptionPane.ERROR_MESSAGE);
            actionAddurl();
        }
    }

    public URL verifyUrl(String url) {
        if (!url.toLowerCase().startsWith("http://")) {
            return null;
        }
        URL verifiedUrl = null;
        try {
            verifiedUrl = new URL(url);
        } catch (Exception e) {
            return null;
        }
        if (verifiedUrl.getFile().length() < 2) {
            return null;
        }
        return verifiedUrl;
    }

    private void tableSelectionChanged() {
        if (selectedDownload != null) {
            selectedDownload.deleteObserver(Main.this);
        }

        if (!clearing && table.getSelectedRow() > -1) {
            selectedDownload = tableModel.getDownload(table.getSelectedRow());
            selectedDownload.addObserver(Main.this);
            updateButtons();
        }
    }

    private void actionPause() {
        selectedDownload.pause();
        updateButtons();
    }

    private void actionResume() {
        selectedDownload.resume();
        updateButtons();
    }

    private void actionCancel() {
        selectedDownload.cancel();
        updateButtons();
    }

    private void actionClear() {
        clearing = true;
        tableModel.clearDownload(table.getSelectedRow());
        clearing = false;
        selectedDownload = null;
        updateButtons();
    }

    private void updateButtons() {
        if (selectedDownload != null) {
            int status = selectedDownload.getStatus();
            switch (status) {
                case Download.DOWNLOADING:
                    pauseButton.setEnabled(true);
                    resumeButton.setEnabled(false);
                    cancelButton.setEnabled(true);
                    clearButton.setEnabled(false);
                    break;

                case Download.PAUSED:
                    pauseButton.setEnabled(false);
                    resumeButton.setEnabled(true);
                    cancelButton.setEnabled(true);
                    clearButton.setEnabled(false);
                    break;

                case Download.ERROR:
                    pauseButton.setEnabled(false);
                    resumeButton.setEnabled(true);
                    cancelButton.setEnabled(false);
                    clearButton.setEnabled(true);
                    break;

                default: // COMPLETE OR CANCELED
                    pauseButton.setEnabled(false);
                    resumeButton.setEnabled(false);
                    cancelButton.setEnabled(false);
                    clearButton.setEnabled(true);
            }
        } else {
            pauseButton.setEnabled(false);
            resumeButton.setEnabled(false);
            cancelButton.setEnabled(false);
            clearButton.setEnabled(false);
        }
    }

    public void update(Observable o, Object arg) {
        if (selectedDownload != null && selectedDownload.equals(o)) {
            updateButtons();
        }
    }

    public static void main(String arg[]) {
        Main manager = new Main();
        manager.setVisible(true);
    }
}
