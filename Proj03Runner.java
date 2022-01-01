import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Proj03Runner extends JFrame{
    private JTextField myUrlBar;
    private JButton backButton;
    private JButton nextButton;
    private int index = 0;
    
    public void run(String site){

        // To store all urls
        ArrayList<String> urls = new ArrayList<String>();

        System.out.println("I certify that this program is my own work\nand is not the work of others. I agree not\nto share my solution with others.\nNathan Lu\n");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Webrowser 2021, Nathan Lu");
        try{
            if(site != null){
                // Index stays at 0
                urls.add(site);

                // Actual GUI
                JEditorPane myPage = new JEditorPane(site);

                myPage.setEditable(false);

                // Panel to hold everything in the top bar
                JPanel panel = new JPanel(new BorderLayout());
                
                // URL bar
                myUrlBar = new JTextField(site,10);
                myUrlBar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try{
                            System.out.println("Navigating to " + e.getActionCommand());
                            myPage.setPage(e.getActionCommand());
                            urls.add(e.getActionCommand());
                            index++;
                            myUrlBar.setText(urls.get(index));
                        } catch (Exception err){
                            myUrlBar.setText("");
                            err.printStackTrace();
                        }
                    }
                });

                // For clicking on url bar, makes prompt disappear and reappear
                myUrlBar.addFocusListener(new FocusListener(){
                    public void focusGained(FocusEvent e) {
                        myUrlBar.setText("");
                    }

                    public void focusLost(FocusEvent e){
                        myUrlBar.setText(urls.get(index));
                    }
                });

                
                // Back and Forward buttons
                backButton = new JButton("Back");
                nextButton = new JButton("Next");

                // Listening for click on buttons
                backButton.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        if(index == 0){
                            System.out.println("Cannot go to previous!");
                        } else {
                            try{
                                index--;
                                System.out.println("Navigating to " + urls.get(index));
                                myPage.setPage(urls.get(index));
                                myUrlBar.setText(urls.get(index));
                            } catch (Exception err){
                                err.printStackTrace();
                            }
                        }
                    }
                });

                nextButton.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        if(urls.size() - 1 == index){
                            System.out.println("Cannot go to next!");
                        } else {
                            try{
                                index++;
                                System.out.println("Navigating to " + urls.get(index));
                                myPage.setPage(urls.get(index));
                                myUrlBar.setText(urls.get(index));
                            } catch (Exception err){
                                err.printStackTrace();
                            }
                        }
                    }
                });


                // Adding to the panel
                panel.add(myUrlBar, BorderLayout.CENTER);
                panel.add(backButton, BorderLayout.WEST);
                panel.add(nextButton, BorderLayout.EAST);

                add(panel, BorderLayout.PAGE_START);

                // Listens for hyperlinks and sets new page when clicked (activated)
                myPage.addHyperlinkListener(new HyperlinkListener(){
                    public void hyperlinkUpdate(HyperlinkEvent e) {
                        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                            try {
                                myPage.setPage(e.getURL());
                                System.out.println("Navigating to " + e.getURL());
                                urls.add(e.getURL().toString());
                                index++;
                                myUrlBar.setText(urls.get(index));
                            } catch (Exception err){
                                err.printStackTrace();
                            }
                        }
                    }
                });

                // Basic scroller for main page
                JScrollPane scroller = new JScrollPane();
                JViewport vp = scroller.getViewport();
                vp.add(myPage);

                // Setting main content page
                this.getContentPane().add(scroller, BorderLayout.CENTER);
                this.setSize(700,700);
                this.setVisible(true);
            }
        } catch(Exception err){
            err.printStackTrace();
        }
    }
}
