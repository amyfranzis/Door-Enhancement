package com.eteks.test;

import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import com.eteks.sweethome3d.io.DefaultFurnitureCatalog;
import com.eteks.sweethome3d.model.FurnitureCatalog;
import com.eteks.sweethome3d.model.Home;
import com.eteks.sweethome3d.model.HomePieceOfFurniture;
import com.eteks.sweethome3d.model.Selectable;
import com.eteks.sweethome3d.plugin.Plugin;
import com.eteks.sweethome3d.plugin.PluginAction;
import com.eteks.sweethome3d.plugin.PluginAction.Property;
import com.eteks.sweethome3d.viewcontroller.HomeController;
import com.eteks.test.DoorPlugin.DoorAction;

public class DoorPlugin extends Plugin {

	/*
	 * getActions() main starting point of plugin
	 * @see com.eteks.sweethome3d.plugin.Plugin#getActions()
	 */
	
	@Override
	public PluginAction[] getActions() {
		// TODO Auto-generated method stub
		return new PluginAction [] {new DoorAction()};
	}
	  
	/*******
	 * Public class DoorAction extends PluginAction
	 * builds tool bar choice for change door
	 * 
	 ******/
	
    public class DoorAction extends PluginAction {
    	public DoorAction() {
           putPropertyValue(Property.NAME, "Change Door");
           putPropertyValue(Property.MENU, "Door Ext");
           // Enables the action by default
           setEnabled(true);
        }
        
        
    	/******
    	 * execute method
    	 * finds selected piece of furniture, executes plugin based on whether selected
    	 * is opened or closed door
    	 *****/
    	
        @Override
        public void execute() {
        	int n = 0;
        	Home home = getHome();
        	HomeController controlHome = getHomeController();
        	List<HomePieceOfFurniture> selectedFurniture =  Home.getFurnitureSubList(home.getSelectedItems());
        	System.out.println(selectedFurniture.get(0).getName());
        	List<Selectable> selected = getHome().getSelectedItems();
        	String furnitureName = selectedFurniture.get(0).getName();
        	float[][] currPts = selectedFurniture.get(0).getPoints();
        	System.out.println("FIRST PTS: " + Arrays.deepToString(currPts));
        	
        	for(int i = 0; i < selected.size(); i++){
        		
        		float sub = 0;
        		float[][] points = selected.get(i).getPoints();
        		float[] toSub = new float[2];
        		float[] axis = new float[4];
        		
        		for (int j = 0; j<2; j++)
        		{
        			System.out.println(points[j][0] + " " + points[j][1]);
        	        axis[j] = points[j][0];
        			axis[j+1] = points[j][1];
        			toSub[j] = points[j][0];
        		}
        		
        		//checks to make sure it is either open door or closed door that is selected 
        		//by getting dimension values
        		
        		sub = toSub[1] - toSub[0];
        		System.out.println(sub);
        		
        		if(sub >= 91.4 && sub <= 91.5){
        			
        			//if the selected furniture is door
        			if(furnitureName.equals("Door")){
        				System.out.println("TRUE DOOR" + axis[0] + " " + axis[1] +" " + axis[2] + " " + axis[3] );
        			n = JOptionPane.showConfirmDialog(  
        	                null,
        	                "Switch to open door?" ,
        	                "",
        	                JOptionPane.YES_NO_OPTION);
        			 if(n == JOptionPane.YES_OPTION)
        			 {
        				 controlHome.delete();
        				 FurnitureCatalog catalog = new DefaultFurnitureCatalog();
        				 HomePieceOfFurniture piece = new HomePieceOfFurniture(
        				 catalog.getCategories().get(2).getFurniture().get(12));
        				 home.addPieceOfFurniture(piece);
        				 piece.move(axis[0], axis[2]);
        			 }
        		   } 
        			
        			//if the selected furniture is the open door, configure so that it switches
        			//to closed door
        			
        			if(furnitureName.equals("Open door")){
        				//System.out.println("OPEN TRUE"+ axis[0] + " " + axis[1] +" " + axis[2] + " " + axis[3]);
            			n = JOptionPane.showConfirmDialog(  
            	                null,
            	                "Switch to closed door?" ,
            	                "",
            	                JOptionPane.YES_NO_OPTION);
            			if(n == JOptionPane.YES_OPTION)
            			{
              			 controlHome.delete();
              			 FurnitureCatalog catalog = new DefaultFurnitureCatalog();
              			 HomePieceOfFurniture piece = new HomePieceOfFurniture(
        	             catalog.getCategories().get(2).getFurniture().get(0));
        	             home.addPieceOfFurniture(piece);
        	           	 piece.move(axis[0], axis[2]);
            			}
        			}
        		}
            }	
     	}
    } 
}
