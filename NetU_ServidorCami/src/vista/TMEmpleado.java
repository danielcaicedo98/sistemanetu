/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.ArrayList;
import java.util.List;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author USUARIO
 */
public class TMEmpleado implements TableModel{
    
    protected EventListenerList listenerList; 
    private List<Empleado> empleados;
    private final int columnas;
    
    public TMEmpleado(){
        columnas = 6;
        listenerList = new EventListenerList(); 
        empleados = new ArrayList<>();
    } 
 
    @Override
    public int getRowCount() {
        return empleados.size();
    }

    @Override
    public int getColumnCount() {
        return columnas;
    }

    @Override
    public String getColumnName(int columnIndex) {
        
        String columnName = "";
        
        switch(columnIndex){
            
            case 0: {
                columnName = "Nombre";                         
            }break;
       
            case 1: {
                columnName = "Correo Electrónico";
            }break;
            
            case 2: {
                columnName = "Código";
            }break;
            
            case 3: {
                columnName = "Sexo";
            }break;

            case 4: {
                columnName = "Dependencia";
            }break;

            case 5: {
                columnName = "Subdependencia";
            }break;            
            
        }
        
        return columnName;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        Empleado empleado = empleados.get(rowIndex);
        String valor = "";
        
        switch(columnIndex){
            
            case 0: {
                valor = empleado.getNombre();
            }break;
       
            case 1: {
                valor = empleado.getCorreo();
            }break;
            
            case 2: {
                valor = "" + empleado.getCodigo();
            }break;
            
            case 3: {
                valor = empleado.getSexo();
            }break;

            case 4: {
                valor = empleado.getnDependencia();
            }break;

            case 5: {
                valor = empleado.getnSubdependencia();
            }break;            
            
        }
        
        return valor;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listenerList.add (TableModelListener.class, l);

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listenerList.remove(TableModelListener.class, l);
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados.clear();
        this.empleados = empleados;
       
    }
    
    public Empleado getEmpleadoAt(int p){
        
        return empleados.get(p);
    }

    

    
}
