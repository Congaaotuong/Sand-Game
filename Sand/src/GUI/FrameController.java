package GUI;

import java.awt.*;
import java.awt.event.*;

public class FrameController {
    protected Frame frame = new Frame();
    protected Holder[] scenes;
    protected CardLayout SceneManager = new CardLayout();
    protected Panel ScenePanel = new Panel(SceneManager);
    protected int currentScene;
    public FrameController(String FrameName, String ImageFile, boolean setResizeable, int Width, int Height){
        FrameBasic(FrameName, ImageFile, setResizeable, Width, Height);
        this.scenes = new Holder[100];
        this.currentScene=-1;
    }
    public void InitFrame(){
        frame.setVisible(true);
        frame.add(ScenePanel);
    }

    public void addScene(Holder panel, int pos){
        if(pos<0 || pos>=100) return;
        scenes[pos] = panel;
        ScenePanel.add(scenes[pos].Name(), scenes[pos]);
    }
    public void ChangeScene(String SceneName){
        SceneManager.show(ScenePanel, SceneName);
        for(int i=0; i<100; i++){
            if(scenes[i].Name() == SceneName){
                currentScene=i;
                break;
            }
        }
    }

    private void FrameBasic(String FrameName, String ImageFile, boolean setResizeable, int Width, int Height){
        frame.setSize(Width, Height);
        frame.setTitle(FrameName);
        Image image = Toolkit.getDefaultToolkit().getImage(ImageFile);
        frame.setIconImage(image);
        frame.setResizable(setResizeable);
        frame.setLayout(new BorderLayout());
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event){
                frame.dispose();
                System.exit(0);
            }
        });
        frame.setBackground(Color.WHITE);;
    }
    public void update(){
        if(currentScene<0) return;
        scenes[currentScene].Activate();
    }
}
