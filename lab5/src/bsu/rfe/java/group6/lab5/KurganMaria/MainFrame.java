package bsu.rfe.java.group6.lab5.KurganMaria;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
@SuppressWarnings("serial")
public class MainFrame extends JFrame {
    // Константы, задающие размер окна приложения, если оно
// не распахнуто на весь экран
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private JMenuItem pauseMenuItem;
    private JMenuItem resumeMenuItem;
    // Поле, по которому прыгают мячи
    private Field field = new Field();
    // Конструктор главного окна приложения
    public MainFrame() {
        super("Программирование и синхронизация потоков");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
// Отцентрировать окно приложения на экране
        setLocation((kit.getScreenSize().width - WIDTH) / 2, (kit.getScreenSize().height - HEIGHT) / 2);
// Установить начальное состояние окна развёрнутым на весь экран
        setExtendedState(MAXIMIZED_BOTH);
// Создать меню
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu ballMenu = new JMenu("Мячи");
        Action addBallAction = new AbstractAction("Добавить мяч") {
            public void actionPerformed(ActionEvent event) {
                field.addBall();
                if (!pauseMenuItem.isEnabled() && !resumeMenuItem.isEnabled()) {
// Ни один из пунктов меню не являются доступными - сделать доступным "Паузу"
                    pauseMenuItem.setEnabled(true);
                }
            }
        };
        menuBar.add(ballMenu);
        ballMenu.add(addBallAction);
        JMenu controlMenu = new JMenu("Управление");
        menuBar.add(controlMenu);
        Action pauseAction = new AbstractAction("Приостановить движение") {
            public void actionPerformed(ActionEvent event) {
                field.pause();
                pauseMenuItem.setEnabled(false);
                resumeMenuItem.setEnabled(true);
            }
        };
        pauseMenuItem = controlMenu.add(pauseAction);
        pauseMenuItem.setEnabled(false);
        Action resumeAction = new AbstractAction("Возобновить движение") {
            public void actionPerformed(ActionEvent event) {
                field.resume();
                pauseMenuItem.setEnabled(true);
                resumeMenuItem.setEnabled(false);
            }
        };
        resumeMenuItem = controlMenu.add(resumeAction);
        resumeMenuItem.setEnabled(false);
        Action pauseActionByRadius = new AbstractAction("Остановить движение мячей радиуса меньше 10") {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < field.getBalls().size(); i++)
                    if (field.getBalls().get(i).getRadius() < 10) {
                        field.getBalls().get(i).setSpeedX(0);
                        field.getBalls().get(i).setSpeedY(0);
                    }
            }
        };

        resumeMenuItem = controlMenu.add(pauseActionByRadius);
        resumeMenuItem.setEnabled(true);

        Action moreSpeed = new AbstractAction("+") {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < field.getBalls().size(); i++) {
                    field.getBalls().get(i).setSpeed(field.getBalls().get(i).getMoreSpeed());
                }
            }
        };
        resumeMenuItem = controlMenu.add(moreSpeed);
        resumeMenuItem.setEnabled(true);

        Action lessSpeed = new AbstractAction("-") {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < field.getBalls().size(); i++) {
                    field.getBalls().get(i).setSpeed(field.getBalls().get(i).getLessSpeed());
                }
            }
        };
        resumeMenuItem = controlMenu.add(lessSpeed);
        resumeMenuItem.setEnabled(true);
// Добавить в центр граничной компоновки поле Field
        getContentPane().add(field, BorderLayout.CENTER);
    }
    // Главный метод приложения
    public static void main(String[] args) {
// Создать и сделать видимым главное окно приложения
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}