package nguyen_finproj;

/**
 * Imports
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Edward
 */
public class mineSweeperGUI implements ActionListener {

    Board board;

    int numBombs = 0;
    int bombs, elapsedTime, gamesPlayed, gamesWon;
    int point = 0;
    int countdown = 0;
    int increment = 1;
    int repeatBlock = 1;

    String difficulty;

    Scanner fileStream;

    Score score, obj;

    ArrayList<Score> statsE = new ArrayList();
    ArrayList<Score> statsM = new ArrayList();
    ArrayList<Score> statsH = new ArrayList();

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    JButton[][] grid;
    JButton easy, medium, hard, exit, replay, viewStats, clearData;
    JFrame frame;
    JLabel title, dimensions, bombCount, empty, clock, bombIcon, clockIcon, label, games;
    JPanel gridPanel, informationPanel, masterPanel, labelPanel, titlePanel, commandPanel;

    Timer timer, explosions;

    BufferedImage img1 = null;
    BufferedImage img2 = null;
    BufferedImage img3 = null;
    BufferedImage img4 = null;

    ImageIcon bomb, flag, blank, explosion, clockPic, bomb1;

    File dataFileE = new File("statsEasy.txt");
    File dataFileM = new File("statsMedium.txt");
    File dataFileH = new File("statsHard.txt");
    File dataFile = new File("gamesPlayed.txt");

    FileWriter out;
    BufferedWriter writeFile;
    FileReader in;
    BufferedReader readFile;

    /**
     * Constructs the opening page. Title Screen: gives options to choose
     * difficulty. Reads scores from file and saves them.
     */
    public mineSweeperGUI() {

        try {
            FileInputStream in = new FileInputStream(dataFileE);
            ObjectInputStream readScore = new ObjectInputStream(in);

            do {

                obj = (Score) readScore.readObject();
                if (obj != null) {
                    statsE.add(obj);
                }

            } while (obj != null);

            readScore.close();
            in.close();
        } catch (EOFException e) {
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException: " + e.getMessage());
        }

        try {
            FileInputStream in = new FileInputStream(dataFileM);
            ObjectInputStream readScore = new ObjectInputStream(in);

            do {

                obj = (Score) readScore.readObject();
                if (obj != null) {
                    statsM.add(obj);
                }

            } while (obj != null);

            readScore.close();
            in.close();
        } catch (EOFException e) {
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException: " + e.getMessage());
        }

        try {
            FileInputStream in = new FileInputStream(dataFileH);
            ObjectInputStream readScore = new ObjectInputStream(in);

            do {

                obj = (Score) readScore.readObject();
                if (obj != null) {
                    statsH.add(obj);
                }

            } while (obj != null);

            readScore.close();
            in.close();
        } catch (EOFException e) {
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException: " + e.getMessage());
        }

        try {
            in = new FileReader(dataFile);
            readFile = new BufferedReader(in);
            fileStream = new Scanner(readFile);

            while (fileStream.hasNextInt()) {
                gamesPlayed = fileStream.nextInt();
                gamesWon = fileStream.nextInt();
            }

            readFile.close();
            fileStream.close();
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }

        frame = new JFrame("MineSweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        masterPanel = new JPanel(new BorderLayout());

        titlePanel = new JPanel();

        title = new JLabel("MineSweeper", JLabel.CENTER);
        title.setFont(new Font("", Font.BOLD, 30));
        titlePanel.add(title);

        labelPanel = new JPanel(new GridLayout(3, 2, 30, 10));

        easy = new JButton("Easy");
        easy.setActionCommand("easy");
        easy.addActionListener(new difficultyListener());
        labelPanel.add(easy);

        dimensions = new JLabel("5X5", JLabel.RIGHT);
        labelPanel.add(dimensions);

        medium = new JButton("Medium");
        medium.setActionCommand("medium");
        medium.addActionListener(new difficultyListener());
        labelPanel.add(medium);

        dimensions = new JLabel("10X10", JLabel.RIGHT);
        labelPanel.add(dimensions);

        hard = new JButton("Hard");
        hard.setActionCommand("hard");
        hard.addActionListener(new difficultyListener());
        labelPanel.add(hard);

        dimensions = new JLabel("20X20", JLabel.RIGHT);
        labelPanel.add(dimensions);

        masterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        masterPanel.add(titlePanel);
        masterPanel.add(labelPanel);

        frame.setContentPane(masterPanel);
        frame.setLayout(new BoxLayout(masterPanel, BoxLayout.PAGE_AXIS));
        frame.pack();
        frame.setBounds(screenSize.width / 2 - frame.getWidth() / 2, screenSize.height / 2 - frame.getHeight() / 2, frame.getWidth(), frame.getHeight());
        frame.setVisible(true);
    }

    /**
     * Constructs the main page. Title Screen: gives options to choose
     * difficulty.
     */
    public void mineSweeperGUI() {
        frame.dispose();
        frame = new JFrame("MineSweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        masterPanel = new JPanel(new BorderLayout());

        titlePanel = new JPanel();

        title = new JLabel("MineSweeper", JLabel.CENTER);
        title.setFont(new Font("", Font.BOLD, 30));
        titlePanel.add(title);

        labelPanel = new JPanel(new GridLayout(3, 2, 30, 10));

        easy = new JButton("Easy");
        easy.setActionCommand("easy");
        easy.addActionListener(new difficultyListener());
        labelPanel.add(easy);

        dimensions = new JLabel("5X5", JLabel.RIGHT);
        labelPanel.add(dimensions);

        medium = new JButton("Medium");
        medium.setActionCommand("medium");
        medium.addActionListener(new difficultyListener());
        labelPanel.add(medium);

        dimensions = new JLabel("10X10", JLabel.RIGHT);
        labelPanel.add(dimensions);

        hard = new JButton("Hard");
        hard.setActionCommand("hard");
        hard.addActionListener(new difficultyListener());
        labelPanel.add(hard);

        dimensions = new JLabel("20X20", JLabel.RIGHT);
        labelPanel.add(dimensions);

        masterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        masterPanel.add(titlePanel);
        masterPanel.add(labelPanel);

        frame.setContentPane(masterPanel);
        frame.setLayout(new BoxLayout(masterPanel, BoxLayout.PAGE_AXIS));
        frame.pack();
        frame.setBounds(screenSize.width / 2 - frame.getWidth() / 2, screenSize.height / 2 - frame.getHeight() / 2, frame.getWidth(), frame.getHeight());
        frame.setVisible(true);
    }

    /**
     * Constructs the page to play the game.
     */
    public void gameScreen() {
        frame.dispose();
        frame = new JFrame("MineSweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        numBombs = 0;
        elapsedTime = 0;
        repeatBlock = 1;

        timer = new Timer(1000, new timerListener());
        explosions = new Timer(500, new explosionListener());

        try {
            img1 = ImageIO.read(new File("bomb.png"));
            img2 = ImageIO.read(new File("flag.png"));
            img3 = ImageIO.read(new File("explosion.png"));
            img4 = ImageIO.read(new File("clock.png"));
        } catch (IOException e) {
        }

        Image dimg1 = img1.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        bomb = new ImageIcon(dimg1);
        Image dimg2 = img2.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        flag = new ImageIcon(dimg2);
        Image dimg3 = img3.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        explosion = new ImageIcon(dimg3);
        Image dimg4 = img4.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        clockPic = new ImageIcon(dimg4);
        Image dimg5 = img1.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        bomb1 = new ImageIcon(dimg5);
        blank = new ImageIcon();

        masterPanel = new JPanel(new BorderLayout());

        gridPanel = new JPanel(new GridLayout(board.grid.length, board.grid[0].length, 0, 0));
        gridPanel.setSize(board.grid.length * 50, board.grid[0].length * 50);

        grid = new JButton[board.grid.length][board.grid[0].length];

        for (int i = 0; i < board.grid.length; i++) {
            for (int j = 0; j < board.grid[0].length; j++) {
                grid[i][j] = new JButton();
                grid[i][j].addMouseListener(new MouseListener());
                if (board.grid[i][j] == 1) {
                    grid[i][j].setBackground(Color.LIGHT_GRAY);
                    grid[i][j].setActionCommand("mine");
                    numBombs++;
                } else if (board.grid[i][j] == 0) {
                    grid[i][j].setBackground(Color.LIGHT_GRAY);
                    grid[i][j].setActionCommand("safe");
                }
                grid[i][j].addActionListener(new clickListener());
                gridPanel.add(grid[i][j]);
            }
        }

        bombs = numBombs;

        informationPanel = new JPanel();
        informationPanel.setSize(board.grid.length * 50 + 10, board.grid[0].length * 50 + 90);

        bombIcon = new JLabel(bomb);
        informationPanel.add(bombIcon);

        bombCount = new JLabel(String.valueOf(bombs));
        informationPanel.add(bombCount);

        for (int i = 0; i < board.grid.length; i++) {
            empty = new JLabel("    ");
            informationPanel.add(empty);
        }

        clockIcon = new JLabel(clockPic);
        informationPanel.add(clockIcon);

        clock = new JLabel(String.valueOf(elapsedTime));
        informationPanel.add(clock);

        masterPanel.add(gridPanel);
        masterPanel.add(informationPanel, BorderLayout.PAGE_END);

        frame.setContentPane(masterPanel);
        frame.setSize(board.grid.length * 50 + 10, board.grid[0].length * 50 + 90);
        frame.setBounds(screenSize.width / 2 - frame.getWidth() / 2, screenSize.height / 2 - frame.getHeight() / 2, frame.getWidth(), frame.getHeight());
        frame.setVisible(true);
    }

    /**
     * Constructs the ending page. Gives information about time, and games
     * played. Gives options to exit, view statistics, and play again.
     */
    public void endScreen() {

        if (timer.isRunning()) {
            timer.stop();
        }

        if (explosions.isRunning()) {
            explosions.stop();
        }

        frame.dispose();
        frame = new JFrame("MineSweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        masterPanel = new JPanel(new BorderLayout());

        titlePanel = new JPanel();

        if (board.gameEndWin()) {
            title = new JLabel("You Win!", JLabel.CENTER);
            title.setFont(new Font("", Font.BOLD, 20));
        } else {
            title = new JLabel("You Lose!", JLabel.CENTER);
            title.setFont(new Font("", Font.BOLD, 20));
        }

        titlePanel.add(title);

        labelPanel = new JPanel(new GridLayout(3, 2, 15, 5));

        label = new JLabel("Time:", JLabel.LEFT);
        labelPanel.add(label);

        clock = new JLabel(String.valueOf(elapsedTime), JLabel.CENTER);
        labelPanel.add(clock);

        label = new JLabel("Total games played: ", JLabel.LEFT);
        labelPanel.add(label);

        games = new JLabel(String.valueOf(gamesPlayed), JLabel.CENTER);
        labelPanel.add(games);

        label = new JLabel("Total games won: ", JLabel.LEFT);
        labelPanel.add(label);

        games = new JLabel(String.valueOf(gamesWon), JLabel.CENTER);
        labelPanel.add(games);

        informationPanel = new JPanel(new BorderLayout());

        viewStats = new JButton("View statistics");
        viewStats.setActionCommand("viewStats");
        viewStats.addActionListener(new replayListener());
        informationPanel.add(viewStats);

        commandPanel = new JPanel(new GridLayout(1, 2, 0, 0));

        exit = new JButton("Exit");
        exit.setActionCommand("exit");
        exit.addActionListener(new replayListener());
        commandPanel.add(exit);

        replay = new JButton("Play again");
        replay.setActionCommand("replay");
        replay.addActionListener(new replayListener());
        commandPanel.add(replay);

        masterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        masterPanel.add(titlePanel);
        masterPanel.add(labelPanel);
        masterPanel.add(informationPanel);
        masterPanel.add(commandPanel);

        frame.setContentPane(masterPanel);
        frame.setLayout(new BoxLayout(masterPanel, BoxLayout.PAGE_AXIS));
        frame.pack();
        frame.setBounds(screenSize.width / 2 - frame.getWidth() / 2, screenSize.height / 2 - frame.getHeight() / 2, frame.getWidth(), frame.getHeight());
        frame.setVisible(true);
    }

    /**
     * Constructs the statistics view option page. Gives the option to view easy
     * scores, medium scores, hard scores, and to clear saved data.
     */
    public void statisticScreen() {

        frame.dispose();
        frame = new JFrame("MineSweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        masterPanel = new JPanel();

        titlePanel = new JPanel();

        title = new JLabel("Statistics: ", JLabel.CENTER);
        title.setFont(new Font("", Font.BOLD, 20));
        titlePanel.add(title);

        commandPanel = new JPanel();

        easy = new JButton("Easy");
        easy.setActionCommand("viewEasy");
        easy.addActionListener(new viewStatsListener());
        commandPanel.add(easy);

        medium = new JButton("Medium");
        medium.setActionCommand("viewMedium");
        medium.addActionListener(new viewStatsListener());
        commandPanel.add(medium);

        hard = new JButton("Hard");
        hard.setActionCommand("viewHard");
        hard.addActionListener(new viewStatsListener());
        commandPanel.add(hard);

        labelPanel = new JPanel();

        exit = new JButton("Close");
        exit.setActionCommand("closeStats");
        exit.addActionListener(new viewStatsListener());
        labelPanel.add(exit);

        informationPanel = new JPanel();

        clearData = new JButton("Clear data");
        clearData.setActionCommand("clearData");
        clearData.addActionListener(new clearDataListener());
        informationPanel.add(clearData);

        masterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        masterPanel.add(titlePanel);
        masterPanel.add(commandPanel);
        masterPanel.add(labelPanel);
        masterPanel.add(informationPanel);

        frame.setContentPane(masterPanel);
        frame.setLayout(new BoxLayout(masterPanel, BoxLayout.PAGE_AXIS));
        frame.pack();
        frame.setBounds(screenSize.width / 2 - frame.getWidth() / 2, screenSize.height / 2 - frame.getHeight() / 2, frame.getWidth(), frame.getHeight());
        frame.setVisible(true);
    }

    /**
     * Creates page to view easy statistics.
     */
    public void statisticScreenEasy() {
        frame.dispose();
        frame = new JFrame("MineSweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Sorts.mergesort(statsE, 0, statsE.size() - 1);

        masterPanel = new JPanel();

        titlePanel = new JPanel();

        title = new JLabel("Statistics: Easy", JLabel.CENTER);
        title.setFont(new Font("", Font.BOLD, 20));
        titlePanel.add(title);

        labelPanel = new JPanel();

        label = new JLabel("Times: ", JLabel.LEFT);
        label.setFont(new Font("", Font.BOLD, 15));
        labelPanel.add(label);

        informationPanel = new JPanel(new GridLayout(0, 1, 15, 5));

        for (int i = 0; i < statsE.size(); i++) {
            clock = new JLabel(String.valueOf(statsE.get(i).getTime()), JLabel.CENTER);
            informationPanel.add(clock);
        }

        commandPanel = new JPanel();

        exit = new JButton("Close");
        exit.setActionCommand("return");
        exit.addActionListener(new viewStatsListener());
        commandPanel.add(exit);

        masterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        masterPanel.add(titlePanel);
        masterPanel.add(labelPanel);
        masterPanel.add(informationPanel);
        masterPanel.add(commandPanel);

        frame.setContentPane(masterPanel);
        frame.setLayout(new BoxLayout(masterPanel, BoxLayout.PAGE_AXIS));
        frame.pack();
        frame.setBounds(screenSize.width / 2 - frame.getWidth() / 2, screenSize.height / 2 - frame.getHeight() / 2, frame.getWidth(), frame.getHeight());
        frame.setVisible(true);
    }

    /**
     * Creates page to view medium statistics.
     */
    public void statisticScreenMedium() {
        frame.dispose();
        frame = new JFrame("MineSweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Sorts.mergesort(statsM, 0, statsM.size() - 1);

        masterPanel = new JPanel();

        titlePanel = new JPanel();

        title = new JLabel("Statistics: Medium", JLabel.CENTER);
        title.setFont(new Font("", Font.BOLD, 20));
        titlePanel.add(title);

        labelPanel = new JPanel();

        label = new JLabel("Times: ", JLabel.LEFT);
        label.setFont(new Font("", Font.BOLD, 15));
        labelPanel.add(label);

        informationPanel = new JPanel(new GridLayout(0, 1, 15, 5));

        for (int i = 0; i < statsM.size(); i++) {
            clock = new JLabel(String.valueOf(statsM.get(i).getTime()), JLabel.CENTER);
            informationPanel.add(clock);
        }

        commandPanel = new JPanel();

        exit = new JButton("Close");
        exit.setActionCommand("return");
        exit.addActionListener(new viewStatsListener());
        commandPanel.add(exit);

        masterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        masterPanel.add(titlePanel);
        masterPanel.add(labelPanel);
        masterPanel.add(informationPanel);
        masterPanel.add(commandPanel);

        frame.setContentPane(masterPanel);
        frame.setLayout(new BoxLayout(masterPanel, BoxLayout.PAGE_AXIS));
        frame.pack();
        frame.setBounds(screenSize.width / 2 - frame.getWidth() / 2, screenSize.height / 2 - frame.getHeight() / 2, frame.getWidth(), frame.getHeight());
        frame.setVisible(true);
    }

    /**
     * Creates page to view hard statistics.
     */
    public void statisticScreenHard() {
        frame.dispose();
        frame = new JFrame("MineSweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Sorts.mergesort(statsH, 0, statsH.size() - 1);

        masterPanel = new JPanel();

        titlePanel = new JPanel();

        title = new JLabel("Statistics: Hard", JLabel.CENTER);
        title.setFont(new Font("", Font.BOLD, 20));
        titlePanel.add(title);

        labelPanel = new JPanel();

        label = new JLabel("Times: ", JLabel.LEFT);
        label.setFont(new Font("", Font.BOLD, 15));
        labelPanel.add(label);

        informationPanel = new JPanel(new GridLayout(0, 1, 15, 5));

        for (int i = 0; i < statsH.size(); i++) {
            clock = new JLabel(String.valueOf(statsH.get(i).getTime()), JLabel.CENTER);
            informationPanel.add(clock);
        }

        commandPanel = new JPanel();

        exit = new JButton("Close");
        exit.setActionCommand("return");
        exit.addActionListener(new viewStatsListener());
        commandPanel.add(exit);

        masterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        masterPanel.add(titlePanel);
        masterPanel.add(labelPanel);
        masterPanel.add(informationPanel);
        masterPanel.add(commandPanel);

        frame.setContentPane(masterPanel);
        frame.setLayout(new BoxLayout(masterPanel, BoxLayout.PAGE_AXIS));
        frame.pack();
        frame.setBounds(screenSize.width / 2 - frame.getWidth() / 2, screenSize.height / 2 - frame.getHeight() / 2, frame.getWidth(), frame.getHeight());
        frame.setVisible(true);
    }

    /**
     * Clears all saved data. Overrides existing files to new blank files. Sets
     * gamesPlayed and gamesWon to 0. Clears ArrayLists.
     */
    class clearDataListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            String eventName = event.getActionCommand();
            if ("clearData".equals(eventName)) {
                statsE = new ArrayList();
                statsM = new ArrayList();
                statsH = new ArrayList();
                gamesPlayed = 0;
                gamesWon = 0;

                try {
                    dataFileE.createNewFile();
                    dataFileM.createNewFile();
                    dataFileH.createNewFile();
                } catch (IOException e) {
                    System.err.println("IOException: " + e.getMessage());
                }

                try {
                    out = new FileWriter(dataFile);
                    writeFile = new BufferedWriter(out);

                    writeFile.write(String.valueOf(gamesPlayed));
                    writeFile.newLine();
                    writeFile.write(String.valueOf(gamesWon));

                    writeFile.close();
                } catch (FileNotFoundException e) {
                    System.err.println("FileNotFoundException: " + e.getMessage());
                } catch (IOException e) {
                    System.err.println("IOException: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Reads commands for grid. When game is won, saves game score to file.
     */
    class clickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            String eventName = event.getActionCommand();

            if ("safe".equals(eventName)) {
                timer.start();
                JButton myButton = (JButton) event.getSource();
                int numMines = 0;
                myButton.setBackground(Color.white);
                myButton.setActionCommand("on");
                for (int i = 0; i < board.grid.length; i++) {
                    for (int j = 0; j < board.grid[0].length; j++) {
                        if ("on".equals(grid[i][j].getActionCommand())) {
                            grid[i][j].setActionCommand("cleared");
                            numMines = board.neighbourMines(i, j);
                            if (board.safeZone(i, j)) {
                                board.safeArea(i, j);
                            }
                        } else {
                            if (numMines > 0) {
                                myButton.setText(String.valueOf(numMines));
                            }
                        }
                    }
                }

                for (int i = 0; i < board.grid.length; i++) {
                    for (int j = 0; j < board.grid[0].length; j++) {
                        if (board.grid[i][j] == 2) {
                            grid[i][j].setBackground(Color.white);
                            grid[i][j].setActionCommand("cleared");
                            if (board.neighbourMines(i, j) > 0) {
                                grid[i][j].setText(String.valueOf(board.neighbourMines(i, j)));
                            }
                        }
                    }
                }
            } else if ("mine".equals(eventName)) {
                timer.start();

                for (int i = 0; i < board.grid.length; i++) {
                    for (int j = 0; j < board.grid[0].length; j++) {
                        grid[i][j].setActionCommand("exploding");
                        grid[i][j].addActionListener(new toEndScreenListener());
                        if (board.grid[i][j] == 1 || board.grid[i][j] == 4) {
                            grid[i][j].setIcon(bomb1);
                        } else if (board.grid[i][j] == 0 || board.grid[i][j] == 2) {
                            board.grid[i][j] = 5;
                        }
                    }
                }

                timer.stop();
                explosions.start();
            }

            for (int i = 0; i < board.grid.length; i++) {
                for (int j = 0; j < board.grid[0].length; j++) {
                    if ("cleared".equals(grid[i][j].getActionCommand())) {
                        board.count--;
                        if (board.neighbourMines(i, j) > 0) {
                            grid[i][j].setText(String.valueOf(board.neighbourMines(i, j)));
                        }
                    }
                }
            }

            if (board.gameEndWin()) {
                timer.stop();
                if (repeatBlock == 1) {
                    repeatBlock++;
                    score = new Score(elapsedTime, difficulty);
                    if (null != difficulty) {
                        switch (difficulty) {
                            case "easy":
                                statsE.add(score);
                                try {
                                    dataFileE = new File("statsEasy.txt");
                                    FileOutputStream out = new FileOutputStream(dataFileE);
                                    ObjectOutputStream writeScore = new ObjectOutputStream(out);
                                    for (int i = 0; i < statsE.size(); i++) {
                                        writeScore.writeObject(statsE.get(i));
                                    }

                                    writeScore.close();
                                    out.close();
                                } catch (FileNotFoundException e) {
                                    System.err.println("FileNotFoundException: " + e.getMessage());
                                } catch (IOException e) {
                                    System.err.println("IOException: " + e.getMessage());
                                }
                                break;
                            case "medium":
                                statsM.add(score);
                                try {
                                    dataFileM = new File("statsMedium.txt");
                                    FileOutputStream out = new FileOutputStream(dataFileM);
                                    ObjectOutputStream writeScore = new ObjectOutputStream(out);
                                    for (int i = 0; i < statsM.size(); i++) {
                                        writeScore.writeObject(statsM.get(i));
                                    }
                                    writeScore.close();
                                    out.close();
                                } catch (FileNotFoundException e) {
                                    System.err.println("FileNotFoundException: " + e.getMessage());
                                } catch (IOException e) {
                                    System.err.println("IOException: " + e.getMessage());
                                }
                                break;
                            case "hard":
                                statsH.add(score);
                                try {
                                    dataFileH = new File("statsHard.txt");
                                    FileOutputStream out = new FileOutputStream(dataFileH);
                                    ObjectOutputStream writeScore = new ObjectOutputStream(out);
                                    for (int i = 0; i < statsH.size(); i++) {
                                        writeScore.writeObject(statsH.get(i));
                                    }
                                    writeScore.close();
                                    out.close();
                                } catch (FileNotFoundException e) {
                                    System.err.println("FileNotFoundException: " + e.getMessage());
                                } catch (IOException e) {
                                    System.err.println("IOException: " + e.getMessage());
                                }
                                break;
                            default:
                                break;
                        }
                    }

                    for (int i = 0; i < board.grid.length; i++) {
                        for (int j = 0; j < board.grid[0].length; j++) {
                            if (board.grid[i][j] == 1 || board.grid[i][j] == 4) {
                                grid[i][j].setIcon(bomb1);
                            }
                            board.grid[i][j] = 5;
                            grid[i][j].setActionCommand("win");
                            grid[i][j].addActionListener(new toEndScreenListener());
                        }
                    }
                }
            } else {
                board.count = board.grid.length * board.grid[0].length - numBombs;
            }
        }
    }

    /**
     * Sets board dimensions.
     */
    class difficultyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            String eventName = event.getActionCommand();

            if ("easy".equals(eventName)) {
                board = new Board(5, 5);
                difficulty = "easy";
            } else if ("medium".equals(eventName)) {
                board = new Board(10, 10);
                difficulty = "medium";
            } else if ("hard".equals(eventName)) {
                board = new Board(20, 20);
                difficulty = "hard";
            }
            gameScreen();
        }
    }

    /**
     * Blows up bombs.
     */
    class explosionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            point++;
            for (int i = 0; i < board.grid.length; i++) {
                for (int j = 0; j < board.grid[0].length; j++) {
                    if (point > increment) {
                        if (board.grid[i][j] == 1 || board.grid[i][j] == 4) {
                            increment++;
                            grid[i][j].setIcon(explosion);
                            board.grid[i][j] = 5;
                        }
                    }
                }
            }
            increment /= numBombs;
            increment++;

            for (int i = 0; i < board.grid.length; i++) {
                for (int j = 0; j < board.grid[0].length; j++) {
                    if (board.grid[i][j] == 5) {
                        countdown++;
                    }
                }
            }

            if (countdown == board.grid.length * board.grid[0].length) {
                explosions.stop();
            } else {
                countdown = 0;
            }
        }
    }

    /**
     * Listener for endScreen. Allows exiting of program, opening main screen
     * and opening of statistics screen.
     */
    class replayListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            String eventName = event.getActionCommand();

            if ("exit".equals(eventName)) {
                frame.dispose();
            } else if ("replay".equals(eventName)) {
                frame.dispose();
                mineSweeperGUI();
            } else if ("viewStats".equals(eventName)) {
                statisticScreen();
            }
        }
    }

    /**
     * Clock timer. Increases elapsed time and updates clock.
     */
    class timerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            elapsedTime++;
            clock.setText(String.valueOf(elapsedTime));
        }
    }

    /**
     * Closes current frame and opens end screen. Updates number of games played
     * and number of games won.
     */
    class toEndScreenListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            String eventName = event.getActionCommand();
            gamesPlayed++;
            if ("exploding".equals(eventName)) {
            } else if ("win".equals(eventName)) {
                gamesWon++;
            }

            try {
                out = new FileWriter(dataFile);
                writeFile = new BufferedWriter(out);

                writeFile.write(String.valueOf(gamesPlayed));
                writeFile.newLine();
                writeFile.write(String.valueOf(gamesWon));

                writeFile.close();
                out.close();
            } catch (FileNotFoundException e) {
                System.err.println("FileNotFoundException: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("IOException: " + e.getMessage());
            }

            frame.dispose();
            endScreen();
        }
    }

    /**
     * Creates screens to view statistics.
     */
    class viewStatsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            String eventName = event.getActionCommand();

            if ("viewEasy".equals(eventName)) {
                statisticScreenEasy();
            } else if ("viewMedium".equals(eventName)) {
                statisticScreenMedium();
            } else if ("viewHard".equals(eventName)) {
                statisticScreenHard();
            } else if ("return".equals(eventName)) {
                statisticScreen();
            } else if ("closeStats".equals(eventName)) {
                endScreen();
            }
        }
    }

    /**
     * Listener allowing for flagging on right click.
     */
    class MouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            JButton myButton = (JButton) e.getSource();
            if ("cleared".equals(myButton.getActionCommand())) {
            } else if ((e.getModifiers() == MouseEvent.BUTTON3_MASK) && (!"flagged".equals(myButton.getActionCommand())) && (!"cleared".equals(myButton.getActionCommand())) && (!"exploding".equals(myButton.getActionCommand()))) {
                myButton.setActionCommand("on");

                for (int i = 0; i < board.grid.length; i++) {
                    for (int j = 0; j < board.grid[0].length; j++) {
                        if ("on".equals(grid[i][j].getActionCommand())) {
                            if (board.grid[i][j] == 1) {
                                board.grid[i][j] = 4;
                            } else if (board.grid[i][j] == 0) {
                                board.grid[i][j] = 3;
                            }
                        }
                    }
                }

                myButton.setIcon(flag);
                myButton.setActionCommand("flagged");
                bombs--;
            } else if ((e.getModifiers() == MouseEvent.BUTTON3_MASK) && ("flagged".equals(myButton.getActionCommand())) && (!"cleared".equals(myButton.getActionCommand())) && (!"exploding".equals(myButton.getActionCommand()))) {
                myButton.setActionCommand("on");

                for (int i = 0; i < board.grid.length; i++) {
                    for (int j = 0; j < board.grid[0].length; j++) {
                        if ("on".equals(grid[i][j].getActionCommand())) {
                            grid[i][j].setActionCommand("unflagged");
                            grid[i][j].setIcon(blank);
                            bombs++;

                            if (board.grid[i][j] == 1 || board.grid[i][j] == 4) {
                                myButton.setActionCommand("mine");
                                board.grid[i][j] = 1;
                            } else if (board.grid[i][j] == 0 || board.grid[i][j] == 2 || board.grid[i][j] == 3) {
                                myButton.setActionCommand("safe");
                                board.grid[i][j] = 0;
                                board.repeatCheck[i][j] = false;
                            }
                        }
                    }
                }

                myButton.setBackground(Color.LIGHT_GRAY);
            }

            bombCount.setText(String.valueOf(bombs));
        }
    }

    private static void runGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        mineSweeperGUI gui = new mineSweeperGUI();
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                runGUI();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
