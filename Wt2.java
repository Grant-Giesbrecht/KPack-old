import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.imageio.*;
import java.io.*;
import java.lang.Math.*;

class Wt2{

	JFrame frame;
	
	public Wt2(){
        frame = new JFrame("KGraph Proto 1");
        frame.setSize(500, 470); //X, Y
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        PlotTrace pt = new PlotTrace();
        for (int i = 0 ; i < 1000 ; i++){
            //            pt.add_point((i/10.0), (i/10.0)%3);
            pt.add_point((i/100.0), Math.sin(i/100.0));
            //            pt.add_point((i/100.0), 0);
        }
        pt.setPlotType(PlotTrace.LINEPLOT);
        
        PlotTrace pt2 = new PlotTrace();
        for (int i = 0 ; i < 1000 ; i++){
            //            pt.add_point((i/10.0), (i/10.0)%3);
            pt2.add_point((i/100.0), (i/100.0));
        }
        pt2.setColor(Color.red);
        pt2.setPlotType(PlotTrace.POINTPLOT);
        
        KPlotSharedData sharedData = new KPlotSharedData();
        sharedData.x_min = 1.5;
        sharedData.y_min = 1.5;
        sharedData.x_max = 15;
        sharedData.y_max = 15;
        sharedData.statusLabel = new JLabel("KGraph Ready");
        
        GraphArea panel = new GraphArea(sharedData);
        panel.setAxes(-2, -2, 12, 12);
        //        panel.setAxes(1, 1, 12, 12);
        panel.addPlotTrace(pt);
        panel.addPlotTrace(pt2);
        panel.drawInGraphAxes(true);
        panel.setGridDashed(true);
        
        JMenuBar mb = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveButton = new JMenuItem("Save");
        JMenuItem exportButton = new JMenuItem("Export Image");
        fileMenu.add(saveButton);
        fileMenu.add(exportButton);
        JMenu graphMenu = new JMenu("Graph");
        JMenuItem adjustTraceButton = new JMenuItem("Edit Traces");
        JMenuItem axesButton = new JMenuItem("Edit Axes");
        final JCheckBoxMenuItem showGridCheckBox = new JCheckBoxMenuItem("Show grid", panel.getDrawGrid());
        GridCheckBoxListener gcbl = new GridCheckBoxListener(panel);
        showGridCheckBox.addItemListener(gcbl);
//        JMenu graphPropMenu = new JMenu("Properties");
        JMenuItem traceButton = new JMenuItem("Trace");
        graphMenu.add(adjustTraceButton);
        graphMenu.add(axesButton);
        graphMenu.add(showGridCheckBox);
//        graphMenu.add(propertiesButton);
        graphMenu.addSeparator();
        graphMenu.add(traceButton);
        JMenu helpMenu = new JMenu("Help");
        JMenuItem docButton = new JMenuItem("Docs");
        JMenuItem aboutButton = new JMenuItem("About");
        helpMenu.add(docButton);
        helpMenu.add(aboutButton);
        mb.add(fileMenu);
        mb.add(graphMenu);
        mb.add(helpMenu);
        frame.setJMenuBar(mb);

        GraphToolBar gtb = new GraphToolBar(sharedData, panel);
        
        frame.add(panel, BorderLayout.CENTER);
        frame.add(gtb, BorderLayout.PAGE_START);
        frame.add(sharedData.statusLabel, BorderLayout.PAGE_END);
        
        frame.setVisible(true);
	}
    
    private class GridCheckBoxListener implements ItemListener{
        
        private GraphArea ga;
        
        public GridCheckBoxListener(GraphArea gai){
            ga = gai;
        }
        
        public void itemStateChanged(ItemEvent e) {
            if(e.getStateChange() == 1){
                ga.setDrawGrid(true);
                ga.repaint();
            }else{
                ga.setDrawGrid(false);
                ga.repaint();
            }
        }
        
    }
    
    public static void main(String[] args){
        Wt2 window = new Wt2();
    }

    class GraphArea extends JPanel{
        
        private ArrayList<PlotTrace> traces;
//        private double x_min, x_max, sharedData.y_min, sharedData.y_max;
        private double xTickSpacing, yTickSpacing, xSubTickSpacing, ySubTickSpacing, xNumericSpacing, yNumericSpacing;
        private boolean automaticTickSpacing;
        
        Color backgroundColor;
        Color axesColor;
        
        private boolean drawInGraphAxes;
        private boolean inGraphAxesDashed;
        
        private boolean drawGrid;
        private boolean gridDashed;
        
        private boolean updateResetAxes;
        
        String xAxisLabel;
        String yAxisLabel;
        String figureTitle;
        
        int area_width, area_height;
        
//        JLabel titleLabel;
//        JLabel xAxisLabel;
//        JLabel yAxisLabel;
        KPlotSharedData sharedData;
        
        double topMargin;
        double bottomMargin;
        double leftMargin;
        double rightMargin;
        
        int subTickHeight = 3;
        int tickHeight = 10;
        int numericHeight = 15;
        
        public GraphArea(KPlotSharedData sharedData){
            
            traces = new ArrayList<PlotTrace>();
            
            backgroundColor = Color.white;
            axesColor = Color.black;
            
            this.sharedData = sharedData;
            
            drawInGraphAxes = true;
            inGraphAxesDashed = false;
            
            drawGrid = true;
            gridDashed = true;
            
            xAxisLabel = "X-Axis";
            yAxisLabel = "Y-Axis";
            figureTitle = "Graph Title";
            
            topMargin = .07;
            bottomMargin = .07;
            leftMargin = .07;
            rightMargin = .05;
            
            xTickSpacing = 1;
            yTickSpacing = 1;
            xSubTickSpacing = -1;
            ySubTickSpacing = -1;
            xNumericSpacing = 5;
            yNumericSpacing = 5;
            automaticTickSpacing = true;

            KGraphMouseListener kgml = new KGraphMouseListener(sharedData, this);
            this.addMouseListener(kgml);
            
            updateResetAxes = true;
        }
        
        private class KGraphMouseListener implements MouseListener{
            private KPlotSharedData sharedData; //shared Data object
            private GraphArea ga;
            
            public KGraphMouseListener(KPlotSharedData kpsd, GraphArea gai){
                this.sharedData = kpsd;
                this.ga = gai;
            }
            //Atchison Topeka and the Santa Fe
            public void mouseClicked(MouseEvent e){
                System.out.println("Mouse clicked at " + e.getX() + ", " + e.getY());
                if (sharedData.mouseFunction == KPlotSharedData.PAN){
                    
                    //Width of window in pixels
                    area_width = ga.getWidth();
                    area_height = ga.getHeight();
                    
                    //Pixel location of click in GRAPH
                    double xpt = e.getX() - ga.leftMargin*area_width;
                    double ypt = (area_height - e.getY()) - ga.bottomMargin*area_height;
                    
                    //Pixel widths of GRAPH
                    double graph_width = area_width * (1 - ga.leftMargin - ga.rightMargin);
                    double graph_height = area_height * (1 - ga.topMargin - ga.bottomMargin);
                    
                    //Numeric ranges of graph
                    double x_range = sharedData.x_max - sharedData.x_min;
                    double y_range = sharedData.y_max - sharedData.y_min;
                    
                    //New X and Y numeric middle for GRAPH
                    double x = (xpt/graph_width)*x_range + sharedData.x_min;
                    double y = (ypt/graph_height)*y_range + sharedData.y_min;
                    
                    sharedData.x_max = x + x_range/2;
                    sharedData.x_min = x - x_range/2;
                    sharedData.y_max = y + y_range/2;
                    sharedData.y_min = y - y_range/2;
                    ga.repaint();
                }
            }
            public void mousePressed(MouseEvent e){}
            public void mouseReleased(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            public void mouseExited(MouseEvent e){}
        }
        
        public void paintComponent(Graphics g){
            
            int area_width = this.getWidth();
            int area_height = this.getHeight();
            this.area_width = area_width;
            this.area_height = area_height;
            
            //draw background
            g.setColor(backgroundColor);
            g.fillRect(0, 0, area_width, area_height);
            
            //Determine the size of the graph
            double range_x = sharedData.x_max - sharedData.x_min;
            double range_y = sharedData.y_max - sharedData.y_min;
            
            //Determine where origin is
//            double zero_correction_x = (Math.abs(sharedData.x_min)/(Math.abs(sharedData.x_max) + Math.abs(sharedData.x_min)))*((1-leftMargin-rightMargin)*area_width);
            double zero_correction_x = -1*(sharedData.x_min)/(range_x) * area_width*(1-leftMargin-rightMargin);
//            double zero_correction_y = (Math.abs(sharedData.y_min)/(Math.abs(sharedData.y_max) + Math.abs(sharedData.y_min)))*((1-topMargin-bottomMargin)*area_height);
            double zero_correction_y = -1*(sharedData.y_min)/(range_y) *area_height*(1-topMargin-bottomMargin);
            
//            g.setColor(Color.red);
//            g.drawLine(0, 0, (int)zero_correction_x, (int)zero_correction_y);
//            g.drawOval((int)(zero_correction_x-10), (int)(zero_correction_y-10), 20, 20);
            
            //draw axes
            g.setColor(axesColor);
            g.drawLine( (int)(area_width*leftMargin), (int)(area_height*topMargin), (int)(area_width*leftMargin), (int)(area_height*(1-bottomMargin)) );
            g.drawLine( (int)(area_width*leftMargin), (int)(area_height*(1-bottomMargin)), (int)(area_width*(1-rightMargin)), (int)(area_height*(1-bottomMargin)) );
            
            //draw axes ticks
            if (automaticTickSpacing) selectTickSpacing();
            double pos = 0;
            if (xSubTickSpacing > 0){ //X-Subticks
                while (pos <= sharedData.x_max){
                    if (pos < sharedData.x_min){
                        pos += xSubTickSpacing;
                        continue;
                    }
                    g.drawLine((int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*(1-bottomMargin)) , (int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*(1-bottomMargin))+subTickHeight);
                    pos += xSubTickSpacing;
                }
                pos = -xSubTickSpacing;
                while (pos >= sharedData.x_min){
                    if (pos > sharedData.x_max){
                        pos -= xSubTickSpacing;
                        continue;
                    }
                    g.drawLine((int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*(1-bottomMargin)) , (int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*(1-bottomMargin))+subTickHeight);
                    pos -= xSubTickSpacing;
                }
            }
            if (xTickSpacing > 0){ //X-Ticks
                pos = 0;
//                System.out.println("Zero Correction X: " + zero_correction_x + " Zero Correction Y: " + zero_correction_y + " Area Width: " + area_width);
                while (pos <= sharedData.x_max){
                    if (pos < sharedData.x_min){
//                        System.out.println("\tSkip pos = " + pos);
                        pos += xTickSpacing;
                        continue;
                    }
//                    System.out.println("Draw pos = " + pos);
                    
                    g.drawLine((int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*(1-bottomMargin)) , (int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*(1-bottomMargin))+tickHeight);
                    pos += xTickSpacing;
                }
                pos = -xTickSpacing;
                while (pos >= sharedData.x_min){
                    if (pos > sharedData.x_max){
                        pos -= xTickSpacing;
                        continue;
                    }
                    g.drawLine((int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*(1-bottomMargin)) , (int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*(1-bottomMargin))+tickHeight);
                    pos -= xTickSpacing;
                }
            }
            if (xNumericSpacing > 0){ //X-Numeric Ticks
                pos = 0;
                while (pos <= sharedData.x_max){
                    if (pos < sharedData.x_min){
                        pos += xNumericSpacing;
                        continue;
                    }
                    g.drawLine((int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*(1-bottomMargin)) , (int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*(1-bottomMargin))+numericHeight);
                    g.drawString("" + pos, (int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*(1-bottomMargin))+numericHeight);
                    pos += xNumericSpacing;
                }
                pos = -xNumericSpacing;
                while (pos >= sharedData.x_min){
                    if (pos > sharedData.x_max){
                        pos -= xNumericSpacing;
                        continue;
                    }
                    g.drawLine((int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*(1-bottomMargin)) , (int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*(1-bottomMargin)) + numericHeight);
                    g.drawString("" + pos, (int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*(1-bottomMargin))+numericHeight);
                    pos -= xNumericSpacing;
                }
            }
            g.setColor(Color.black);
            if (ySubTickSpacing > 0){ //y-Subticks
                pos = 0;
                while (pos <= sharedData.y_max){
                    if (pos < sharedData.y_min){
                        pos += ySubTickSpacing;
                        continue;
                    }
                    g.drawLine( (int)(area_width*leftMargin), (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y), (int)(area_width*leftMargin)-subTickHeight, (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y));
                    pos += ySubTickSpacing;
                }
                pos = -ySubTickSpacing;
                while (pos >= sharedData.y_min){
                    if (pos > sharedData.y_max){
                        pos -= ySubTickSpacing;
                        continue;
                    }
                    g.drawLine( (int)(area_width*leftMargin), (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y), (int)(area_width*leftMargin)-subTickHeight, (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y));
                    pos -= ySubTickSpacing;
                }
                System.out.println("\t " + ySubTickSpacing + " " + sharedData.y_min);
            }
            if (yTickSpacing > 0){ //y-ticks
                pos = 0;
                while (pos <= sharedData.y_max){
                    if (pos < sharedData.y_min){
                        pos += yTickSpacing;
                        continue;
                    }
                    g.drawLine( (int)(area_width*leftMargin)-tickHeight, (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y), (int)(area_width*leftMargin), (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y));
                    pos += yTickSpacing;
                }
                pos = -yTickSpacing;
                while (pos >= sharedData.y_min){
                    if (pos > sharedData.y_max){
                        pos -= yTickSpacing;
                        continue;
                    }
                    g.drawLine( (int)(area_width*leftMargin)-tickHeight, (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y), (int)(area_width*leftMargin), (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y));
                    pos -= yTickSpacing;
                }
            }
            if (yNumericSpacing > 0){ //y-numeral ticks
                pos = 0;
                while (pos <= sharedData.y_max){
                    if (pos < sharedData.y_min){
                        pos += yNumericSpacing;
                        continue;
                    }
                    g.drawLine( (int)(area_width*leftMargin)-numericHeight, (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y), (int)(area_width*leftMargin), (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y));
                    g.drawString("" + pos, (int)(area_width*leftMargin)-numericHeight-10, (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y));
                    pos += yNumericSpacing;
                }
                pos = -yNumericSpacing;
                while (pos >= sharedData.y_min){
                    if (pos > sharedData.y_max){
                        pos -= yNumericSpacing;
                        continue;
                    }
                    g.drawLine( (int)(area_width*leftMargin)-numericHeight, (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y), (int)(area_width*leftMargin), (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y));
                    g.drawString("" + pos, (int)(area_width*leftMargin)-numericHeight-10, (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y));
                    pos -= yNumericSpacing;
                }
            }
            
//            System.out.println(area_height);
//            System.out.println(area_width);
            
            //label axes
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, (int)(.03515625*area_height)));
            g.drawString(xAxisLabel, (int)(area_width*.45), (int)(area_height*.99));
            g.setFont(new Font(Font.DIALOG, Font.PLAIN, (int)(0.05468750*area_height)));
//            System.out.println(g.getFont());
            g.drawString(figureTitle, (int)(area_width*.42), (int)(.05*area_height));
            
//            System.out.println("Title Font Size: " + (int)(.05*area_height));
//            System.out.println("\n" + xSubTickSpacing + " " + xTickSpacing + " " + xNumericSpacing + "\n");
            
            //Draw in-graph axes if requested and applicable
            if (drawInGraphAxes){
                if ((sharedData.x_min < 0 || sharedData.x_max < 0) && (sharedData.x_max > 0 || sharedData.x_min > 0)){
                    g.setColor(Color.gray);
                    if (inGraphAxesDashed){
                        drawDashedLine(g, (int)(area_width*leftMargin + zero_correction_x), (int)(area_height*(1-bottomMargin) - sharedData.y_min/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y) , (int)(area_width*leftMargin + zero_correction_x), (int)(area_height*(1-bottomMargin) - sharedData.y_max/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y), 10, 5);
                    }else{
                        g.drawLine((int)(area_width*leftMargin + zero_correction_x), (int)(area_height*(1-bottomMargin) - sharedData.y_min/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y) , (int)(area_width*leftMargin + zero_correction_x), (int)(area_height*(1-bottomMargin) - sharedData.y_max/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y));
                    }
                }
                if ((sharedData.y_min < 0 || sharedData.y_max < 0) && (sharedData.y_max > 0 || sharedData.y_min > 0)){
                    g.setColor(Color.gray);
                    if (inGraphAxesDashed){
                        drawDashedLine(g, (int)(area_width*leftMargin + sharedData.x_min/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x) , (int)(area_height*(1-bottomMargin) - zero_correction_y), (int)(area_width*leftMargin + sharedData.x_max/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x) ,(int)(area_height*(1-bottomMargin) - zero_correction_y), 10, 5 );
                    }else{
                        g.drawLine((int)(area_width*leftMargin + sharedData.x_min/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x) , (int)(area_height*(1-bottomMargin) - zero_correction_y), (int)(area_width*leftMargin + sharedData.x_max/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x) ,(int)(area_height*(1-bottomMargin) - zero_correction_y));
                    }
                }
            }
            
            //Lad clifton spirit and the frog galiard
            
            //Draw grid if requested
            g.setColor(Color.blue);
            if (drawGrid){
                if (gridDashed){
                    if (xTickSpacing > 0){
                        pos = 0;
                        while (pos <= sharedData.x_max){
                            if (pos < sharedData.x_min){
                                pos += xTickSpacing;
                                continue;
                            }
                            drawDashedLine(g, (int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*(1-bottomMargin)) , (int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*topMargin), 1, 4);
                            pos += xTickSpacing;
                        }
                        pos = -xTickSpacing;
                        while (pos >= sharedData.x_min){
                            if (pos > sharedData.x_max){
                                pos -= xTickSpacing;
                                continue;
                            }
                            drawDashedLine(g, (int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*(1-bottomMargin)) , (int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*topMargin), 1, 4);
                            pos -= xTickSpacing;
                        }
                    }
                    if (yTickSpacing > 0){
                        pos = 0;
                        while (pos <= sharedData.y_max){
                            if (pos < sharedData.y_min){
                                pos += yTickSpacing;
                                continue;
                            }
                            drawDashedLine(g, (int)(area_width*leftMargin), (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y), (int)(area_width*(1-rightMargin)), (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y), 1, 4);
                            pos += yTickSpacing;
                        }
                        pos = -yTickSpacing;
                        while (pos >= sharedData.y_min){
                            if (pos > sharedData.y_max){
                                pos -= yTickSpacing;
                                continue;
                            }
                            drawDashedLine(g, (int)(area_width*leftMargin), (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y), (int)(area_width*(1-rightMargin)), (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y), 1, 4);
                            pos -= yTickSpacing;
                        }
                    }
                }else{
                    if (xTickSpacing > 0){ //X-Ticks
                        pos = 0;
                        while (pos <= sharedData.x_max){
                            if (pos < sharedData.x_min){
                                pos += xTickSpacing;
                                continue;
                            }
                            g.drawLine((int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*(1-bottomMargin)) , (int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*topMargin));
                            pos += xTickSpacing;
                        }
                        pos = -xTickSpacing;
                        while (pos >= sharedData.x_min){
                            if (pos > sharedData.x_max){
                                pos -= xTickSpacing;
                                continue;
                            }
                            g.drawLine((int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*(1-bottomMargin)) , (int)(area_width*leftMargin + pos/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x), (int)(area_height*topMargin));
                            pos -= xTickSpacing;
                        }
                    }
                    if (yTickSpacing > 0){ //y-ticks
                        pos = 0;
                        while (pos <= sharedData.y_max){
                            if (pos < sharedData.y_min){
                                pos += xTickSpacing;
                                continue;
                            }
                            g.drawLine( (int)(area_width*leftMargin), (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y), (int)(area_width*(1-rightMargin)), (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y));
                            pos += yTickSpacing;
                        }
                        pos = -yTickSpacing;
                        while (pos >= sharedData.y_min){
                            if (pos > sharedData.y_max){
                                pos -= xTickSpacing;
                                continue;
                            }
                            g.drawLine( (int)(area_width*leftMargin), (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y), (int)(area_width*(1-rightMargin)), (int)(area_height*(1-bottomMargin) - pos/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y));
                            pos -= yTickSpacing;
                        }
                    }
                }
            }
            
            //Plot points
            PlotTrace pt;
            Color tc;
            double x, y;
            int xpt, ypt;
            int thickness;
            for (int i = 0 ; i < traces.size() ; i++){
                pt = traces.get(i);
                if (pt.plotType != 1){
                    continue;
                }
                g.setColor(pt.getColor());
                thickness = pt.getLineThickness();
                
                for (int j = 0 ; j < pt.getNumPts() ; j++){
                    x = pt.getX(j);
                    y = pt.getY(j);
                    
                    xpt = (int)(area_width*leftMargin + x/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x);
                    ypt = (int)(area_height*(1-bottomMargin) - y/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y);
                    
                    if (!(x < sharedData.x_min || x > sharedData.x_max || y < sharedData.y_min || y > sharedData.y_max)){
                        g.fillRect(xpt, ypt, thickness, thickness);
                    }
                }
            }
            
            //Plot lines
            int xpt_old = -1;
            int ypt_old = -1;
            for (int i = 0 ; i < traces.size() ; i++){
                pt = traces.get(i);
                if (pt.plotType != 0){
                    continue;
                }
                g.setColor(pt.getColor());
                thickness = pt.getLineThickness();
                
                if (pt.getNumPts() < 1){
                    continue;
                }
                
                x = pt.getX(0);
                y = pt.getY(0);
                xpt_old = (int)(area_width*leftMargin + x/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x);
                ypt_old = (int)(area_height*(1-bottomMargin) - y/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y);
                
                for (int j = 1 ; j < pt.getNumPts() ; j++){
                    x = pt.getX(j);
                    y = pt.getY(j);
                    
                    xpt = (int)(area_width*leftMargin + x/range_x*area_width*(1-leftMargin-rightMargin) + zero_correction_x);
                    ypt = (int)(area_height*(1-bottomMargin) - y/range_y*area_height*(1-bottomMargin-topMargin) - zero_correction_y);
                    
                    if (!(x < sharedData.x_min || x > sharedData.x_max || y < sharedData.y_min || y > sharedData.y_max)){
                        g.drawLine(xpt_old, ypt_old, xpt, ypt);
                    }
                    xpt_old = xpt;
                    ypt_old = ypt;
                    
                }
            }
            
            if (updateResetAxes){
                updateResetAxes = false;
                sharedData.resetXMin = sharedData.x_min;
                sharedData.resetXMax = sharedData.x_max;
                sharedData.resetYMin = sharedData.y_min;
                sharedData.resetYMax = sharedData.y_max;
            }
            
        }
        
        private void selectTickSpacing(){
            
            double x_range = sharedData.x_max - sharedData.x_min;
            double y_range = sharedData.y_max - sharedData.y_min;
            
            if (x_range < 20){
                xNumericSpacing = 5;
                xTickSpacing = 1;
                xSubTickSpacing = .1;
            }else if(x_range < 100){
                xNumericSpacing = 10;
                xTickSpacing = 1;
                xSubTickSpacing = -1;
            }else{
//                xNumericSpacing = 
            }
            
            if (y_range < 20){
                yNumericSpacing = 5;
                yTickSpacing = 1;
                ySubTickSpacing = .1;
            }else if(y_range < 100){
                yNumericSpacing = 10;
                yTickSpacing = 1;
                ySubTickSpacing = -1;
            }else{
//                
            }
            
        }
        
        public void drawTick(Graphics g, double x, double y){
            g.drawLine((int)(area_width*x), (int)(area_height*.05), (int)(area_width*x), (int)(area_height*y));
        }
        
        public void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2, int dashSize, int spaceSize){
            
            if (x1 > x2){
                int a = x1;
                x1 = x2;
                x2 = a;
            }
            
            if (y1 > y2){
                int a = y1;
                y1 = y2;
                y2 = a;
            }
            
            
            if (dashSize < 1){
                dashSize = 1;
            }
            
            int slope, x_dist, y_dist, x_dist_space, y_dist_space;
            if (x2 == x1){
                x_dist = 0;
                x_dist_space = 0;
                y_dist = dashSize;
                y_dist_space = spaceSize;
            }else{
                slope = (y2 - y1)/(x2 - x1);
            
                x_dist = (int)Math.sqrt(dashSize*dashSize/(slope*slope + 1));
                y_dist = (int)(slope * x_dist);
                x_dist_space = (int)Math.sqrt(spaceSize*spaceSize/(slope*slope + 1));
                y_dist_space = (int)(slope * x_dist_space);
            }
            
            int x_old, y_old, x_new, y_new;
            
            x_old = x1;
            y_old = y1;
            
            while (true){
                x_new = x_old + x_dist;
                y_new = y_old + y_dist;
//                System.out.println(">" + x_old + " " + y_old + " " + x_new + " " + y_new);
                if ((x_new >= x2) && (y_new >= y2)){
//                    System.out.println("!");
                    x_new = x2;
                    y_new = y2;
//                    System.out.println(x2 + " " + y2);
                    g.drawLine(x_old, y_old, x_new, y_new);
                    break;
                }
                g.drawLine(x_old, y_old, x_new, y_new);
                x_old = x_new + x_dist_space;
                y_old = y_new + y_dist_space;
                if (x_old >= x2 && y_old >= y2) break;
            }
            
        }
        
        private void draw_point(Graphics g, int x, int y){
//            g.fillRect(x-1, y-1, x+1, y+1);
            g.fillRect(x, y, 2, 2);
        }
        
        public void addPlotTrace(PlotTrace pt){
            traces.add(pt);
        }
        
        public void setAxes(double new_x_min, double new_y_min, double new_x_max, double new_y_max){
            this.sharedData.x_max = new_x_max;
            this.sharedData.x_min = new_x_min;
            this.sharedData.y_max = new_y_max;
            this.sharedData.y_min = new_y_min;
//            System.out.println(">" + sharedData.x_min + " " + sharedData.y_min + " " + sharedData.x_max + " " + sharedData.y_max);
        }
        
        public double getXMin(){
            return sharedData.x_min;
        }
        
        public double getXMax(){
            return sharedData.x_max;
        }

        public double getYMin(){
            return sharedData.y_min;
        }
        
        public double getYMax(){
            return sharedData.y_max;
        }
        
        public void drawInGraphAxes(boolean b){
            drawInGraphAxes = b;
        }
        
        public boolean getDrawInGraphAxes(){
            return drawInGraphAxes;
        }
        
        public void setGridDashed(boolean b){
            gridDashed = b;
        }
        
        public boolean getGridDashed(boolean b){
            return gridDashed;
        }
        
        public boolean getUpdateResetAxes(){
            return updateResetAxes;
        }
        
        public void setUpdateResetAxes(boolean b){
            updateResetAxes = b;
        }
        
        public void setDrawGrid(boolean b){
            drawGrid = b;
        }
        
        public boolean getDrawGrid(){
            return drawGrid;
        }
    }
    
    class PlotTrace{
        
        private ArrayList<Double> x_values;
        private ArrayList<Double> y_values;
        
        private Color traceColor;
        
        int lineType; //0 = solid, 1 = dashed, 2 = dotted, 3 = dash-dotted OR 0 = point, 1 = asterisk
        int plotType; //0 - line, 1 - points
        int lineThickness;
        
        //plotType values
        public static final int LINEPLOT = 0; //Plot using lines connecting the points
        public static final int POINTPLOT = 1; //Plot points at each point - don't connect
        
        //lineType values
        
        public PlotTrace(){
            x_values = new ArrayList<Double>();
            y_values = new ArrayList<Double>();
            traceColor = Color.blue;
            lineType = 0;
            plotType = 0;
            lineThickness = 1;
        }
        
        public void add_point(double x, double y){
            x_values.add(x);
            y_values.add(y);
        }
        
        public void setValues(ArrayList<Double> x, ArrayList<Double> y){
            x_values = x;
            y_values = y;
        }
        
        public void setColor(Color c){
            traceColor = c;
        }
        
        public Color getColor(){
            return traceColor;
        }
        
        public double getX(int idx){
            return x_values.get(idx).doubleValue();
        }
        
        public double getY(int idx){
            return y_values.get(idx).doubleValue();
        }
        
        public int getNumPts(){
            return x_values.size();
        }
        
        public int getLineThickness(){
            return lineThickness;
        }
        
        public void setLineThickness(int thickness){
            lineThickness = thickness;
        }
        
        public int getLineType(){
            return lineType;
        }
        
        public void setLineType(int type){
            lineType = type;
        }
        
        public int getPlotType(){
            return plotType;
        }
        
        public void setPlotType(int nplotType){
            this.plotType = nplotType;
        }
    }

    class GraphToolBar extends JToolBar{
        
        KPlotSharedData sharedData;
        GraphArea ga;
        
        public GraphToolBar(KPlotSharedData sharedData, GraphArea inputGA){
            
            this.sharedData = sharedData;
            this.ga = inputGA;
            
            this.setFloatable(false);
            this.setRollover(true);
//            this.setPreferredSize(new Dimension(100, 100));
//            this.setMaximumSize(new Dimension(50, 50));
            
            JLabel spacer1 = new JLabel("  ");
            JLabel spacer2 = new JLabel(" ");
            JLabel spacer3 = new JLabel(" ");
            JLabel spacer4 = new JLabel(" ");
            
            JButton zoomInButton = new JButton(""); //Zoom In
            zoomInButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    double a = sharedData.x_min;
                    double b = sharedData.y_min;
                    double c = sharedData.x_max;
                    double d = sharedData.y_max;
                    double x_range = sharedData.x_max - sharedData.x_min;
                    double y_range = sharedData.y_max - sharedData.y_min;
                    sharedData.x_max = sharedData.x_max - x_range/4;
                    sharedData.y_max = sharedData.y_max - y_range/4;
                    sharedData.x_min = sharedData.x_min + x_range/4;
                    sharedData.y_min = sharedData.y_min + y_range/4;
                    ga.repaint();
//                    System.out.println("Zoom in: " + a + " " + b + " " + c + " " + d + " | " + sharedData.x_min + " " + sharedData.y_min + " " + sharedData.x_max + " " + sharedData.y_max);
                    System.out.println("\n X:(" + sharedData.x_min + ", " + sharedData.x_max +") Y(" + sharedData.y_min + ", " + sharedData.y_max + ")");
                    sharedData.mouseFunction = KPlotSharedData.ZOOM_IN;
                    sharedData.statusLabel.setText("Zoom In with Mouse");
                }
            });
            zoomInButton.setToolTipText("Zoom in");
            try{
                InputStream instrm = getClass().getResourceAsStream("zoom_in.png");
                if (instrm != null){
                    zoomInButton.setIcon(new ImageIcon(ImageIO.read(instrm)));
                }else{
                    zoomInButton.setText("Zoom In");
                    //                    System.out.println("Error failed to locate resource file: 'zoom_reset.png'");
                }
            }catch(IOException e){
            }
            
            JButton resetZoom = new JButton(""); //Reset Zoom
            resetZoom.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
//                    System.out.println("Reset Zoom: " + sharedData.resetXMin + " " + sharedData.resetXMax + " " + sharedData.resetYMin + " " + sharedData.resetYMax);
                    sharedData.x_min = sharedData.resetXMin;
                    sharedData.x_max = sharedData.resetXMax;
                    sharedData.y_min = sharedData.resetYMin;
                    sharedData.y_max = sharedData.resetYMax;
                    ga.repaint();
                }
            });
            resetZoom.setToolTipText("Reset zoom to default");
            try{
                InputStream instrm = getClass().getResourceAsStream("zoom_reset.png");
                if (instrm != null){
                    resetZoom.setIcon(new ImageIcon(ImageIO.read(instrm)));
                }else{
                    resetZoom.setText("Reset Zoom");
//                    System.out.println("Error failed to locate resource file: 'zoom_reset.png'");
                }
            }catch(IOException e){
            }
            
            JButton zoomOutButton = new JButton(""); //Zoom Out
            zoomOutButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    double a = sharedData.x_min;
                    double b = sharedData.y_min;
                    double c = sharedData.x_max;
                    double d = sharedData.y_max;
                    double x_range = sharedData.x_max - sharedData.x_min;
                    double y_range = sharedData.y_max - sharedData.y_min;
                    sharedData.x_max = sharedData.x_max + x_range/2;
                    sharedData.y_max = sharedData.y_max + y_range/2;
                    sharedData.x_min = sharedData.x_min - x_range/2;
                    sharedData.y_min = sharedData.y_min - y_range/2;
                    ga.repaint();
//                    System.out.println("\nZoom out: " + a + " " + b + " " + c + " " + d + " | " + sharedData.x_min + " " + sharedData.y_min + " " + sharedData.x_max + " " + sharedData.y_max + "\n");
                    System.out.println("\n X:(" + sharedData.x_min + ", " + sharedData.x_max +") Y(" + sharedData.y_min + ", " + sharedData.y_max + ")");
                    sharedData.mouseFunction = KPlotSharedData.ZOOM_OUT;
                    sharedData.statusLabel.setText("Zoom Out with Mouse");
                }
            });
            zoomOutButton.setToolTipText("Zoom out");
            try{
                InputStream instrm = getClass().getResourceAsStream("zoom_out.png");
                if (instrm != null){
                    zoomOutButton.setIcon(new ImageIcon(ImageIO.read(instrm)));
                }else{
                    zoomOutButton.setText("Zoom Out");
                    //                    System.out.println("Error failed to locate resource file: 'zoom_reset.png'");
                }
            }catch(IOException e){
            }
            
            JButton releaseMouse = new JButton(""); //Release Mouse
            releaseMouse.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    sharedData.mouseFunction = KPlotSharedData.NO_FUNCTION;
                    sharedData.statusLabel.setText("KGraph Ready");
                }
            });
            releaseMouse.setToolTipText("Release mouse");
            try{
                InputStream instrm = getClass().getResourceAsStream("release_mouse.png");
                if (instrm != null){
                    releaseMouse.setIcon(new ImageIcon(ImageIO.read(instrm)));
                }else{
                    releaseMouse.setText("Release Mouse");
                    //                    System.out.println("Error failed to locate resource file: 'zoom_reset.png'");
                }
            }catch(IOException e){
            }
            
            JButton panButton = new JButton(""); //Pan
            panButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    sharedData.mouseFunction = KPlotSharedData.PAN;
                    sharedData.statusLabel.setText("Pan with Mouse");
                }
            });
            panButton.setToolTipText("Pan graph");
            try{
                InputStream instrm = getClass().getResourceAsStream("pan.png");
                if (instrm != null){
                    panButton.setIcon(new ImageIcon(ImageIO.read(instrm)));
                }else{
                    panButton.setText("Pan");
                    //                    System.out.println("Error failed to locate resource file: 'zoom_reset.png'");
                }
            }catch(IOException e){
            }
            
//            JRadioButton rb1 = new JRadioButton("Radio Button");
            
            JButton traceButton = new JButton(""); //Trace
            traceButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    sharedData.mouseFunction = KPlotSharedData.TRACE_POINTS;
                    sharedData.statusLabel.setText("Trace with Mouse");
                }
            });
            traceButton.setToolTipText("Trace plots");
            try{
                InputStream instrm = getClass().getResourceAsStream("trace.png");
                if (instrm != null){
                    traceButton.setIcon(new ImageIcon(ImageIO.read(instrm)));
                }else{
                    traceButton.setText("Trace");
                    //                    System.out.println("Error failed to locate resource file: 'zoom_reset.png'");
                }
            }catch(IOException e){
            }
            
            JButton markupButton = new JButton(""); //Markup
            markupButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    sharedData.mouseFunction = KPlotSharedData.MARKUP;
                    sharedData.statusLabel.setText("Markup Graph");
                }
            });
            markupButton.setToolTipText("Markup graph");
            try{
                InputStream instrm = getClass().getResourceAsStream("markup.png");
                if (instrm != null){
                    markupButton.setIcon(new ImageIcon(ImageIO.read(instrm)));
                }else{
                    markupButton.setText("Markup");
                    //                    System.out.println("Error failed to locate resource file: 'zoom_reset.png'");
                }
            }catch(IOException e){
            }
            
            this.add(spacer1);
            this.add(zoomInButton);
            this.add(spacer2);
            this.add(resetZoom);
            this.add(spacer3);
            this.add(zoomOutButton);
            this.addSeparator();
            this.add(releaseMouse);
            this.addSeparator();
            this.add(panButton);
            this.addSeparator();
            this.add(traceButton);
            this.add(spacer4);
            this.add(markupButton);

            
        }

    }

    class KPlotSharedData{
        
        public double x_min, y_min, x_max, y_max;
        
        public double resetXMin, resetXMax, resetYMin, resetYMax;
        
        public JLabel statusLabel;
        
        public int mouseFunction;
        public static final int NO_FUNCTION = 0;
        public static final int ZOOM_IN = 1;
        public static final int ZOOM_OUT = 2;
        public static final int TRACE_POINTS = 3;
        public static final int PAN = 4;
        public static final int MARKUP = 5;
        
        public KPlotSharedData(){
            mouseFunction = NO_FUNCTION;
            resetXMin = -1;
            resetXMax = -1;
            resetYMin = -1;
            resetYMax = -1;
        }
        
    }
}

/*
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Graph extends JFrame {
 
	JFrame f = new JFrame();
	JPanel jp;
 
	public Graph() {
        f.setTitle("Simple Drawing");
        f.setSize(300, 300);
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);

        jp = new GPanel();
        f.add(jp);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        Graph g1 = new Graph();
        g1.setVisible(true);
    }

    class GPanel extends JPanel {
        public GPanel() {
            f.setPreferredSize(new Dimension(300, 300));
        }

        @Override
        public void paintComponent(Graphics g) {
            //rectangle originates at 10,10 and ends at 240,240
            g.drawRect(10, 10, 240, 240);
            //filled Rectangle with rounded corners.
            g.fillRoundRect(50, 50, 100, 100, 80, 80);
        }
 	}
}
*/
