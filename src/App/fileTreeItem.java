package App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class fileTreeItem extends TreeItem<File>{

    private Image directoryImg = new Image(getClass().getResourceAsStream("/Assets/directory.png"));
    private Image fileImg = new Image(getClass().getResourceAsStream("/Assets/file.png"));
    private boolean isFirstTimeChildren = true;
    private boolean isFirstTimeLeaf = true;
    private boolean isLeaf;
    private Node image;


    public fileTreeItem(File f) {
        super(f);
        directoryImg = new Image(getClass().getResourceAsStream("/Assets/directory.png"));
        fileImg = new Image(getClass().getResourceAsStream("/Assets/file.png"));
    }

    @Override
    public ObservableList<TreeItem<File>> getChildren() {
        if (isFirstTimeChildren) {
            isFirstTimeChildren = false;
            super.getChildren().setAll(buildChildren(this));
        }
        return super.getChildren();
    }


    @Override
    public boolean isLeaf() {
        if (isFirstTimeLeaf) {
            isFirstTimeLeaf = false;
            File f = (File) getValue();
            isLeaf = f.isFile();
        }

        return isLeaf;
    }


    private ObservableList<TreeItem<File>> buildChildren(TreeItem<File> TreeItem) {
        File f = TreeItem.getValue();
        if (f != null && f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
                ObservableList<TreeItem<File>> children = FXCollections
                        .observableArrayList();

                for (File childFile : files) {
                    children.add(new fileTreeItem(childFile));
                    int size=children.size()-1;


                    if(children.get(size).getValue().isDirectory()) {
                       image = new ImageView(directoryImg);
                       children.get(size).setGraphic(image);
                    } else if(children.get(size).getValue().isFile()){
                       image = new ImageView(fileImg);
                       children.get(children.size()-1).setGraphic(image);
                    }


                }

                return children;
            }
        }

        return FXCollections.emptyObservableList();
    }


}


