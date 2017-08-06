import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScriptTab extends JPanel {
    private ScriptData data;

    public ScriptTab(ScriptData data) {
        this.data=data;
        arrangeLayout(data);
    }

    public ScriptData getData()
    {
        return data;
    }

    private void arrangeLayout(ScriptData data) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(200,200));

        CardLayout layout = new CardLayout();
        JPanel cardStack = new JPanel(layout);
        cardStack.setPreferredSize(new Dimension(200,180));

        cardStack.add(makeFirstCard(layout, cardStack, data), "1");
        cardStack.add(makeSecondCard(layout, cardStack, data), "2");

        add(cardStack);
    }

    private JPanel makeFirstCard(CardLayout layout, JPanel cardStack, ScriptData data) {
        JPanel firstCard = new JPanel(new GridLayout(5, 1));
        firstCard.add(new JLabel(data.getName(), SwingConstants.CENTER));
        firstCard.add(new JLabel(String.valueOf(data.getVersion()), SwingConstants.CENTER));
        firstCard.add(new JLabel(data.getAuthor(), SwingConstants.CENTER));

        JButton descButton = new JButton("Description");
        descButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout.show(cardStack, "2");
            }
        });

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO:add code to start script
            }
        });

        firstCard.add(descButton);
        firstCard.add(startButton);

        return firstCard;
    }

    private JPanel makeSecondCard(CardLayout layout, JPanel cardStack, ScriptData data) {
        JPanel secondCard = new JPanel(new BorderLayout());
        secondCard.setPreferredSize(new Dimension(cardStack.getWidth(),cardStack.getHeight()));

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout.show(cardStack, "1");
            }
        });

        JTextArea textArea = new JTextArea(data.getDesc());
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        secondCard.add(scrollPane, BorderLayout.CENTER);
        secondCard.add(backButton, BorderLayout.NORTH);

        return secondCard;
    }
}
