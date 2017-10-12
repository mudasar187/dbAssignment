package Program;

import java.util.ArrayList;

/**
 *
 * @author Mudasar Ahmad
 * @version 1.0
 *
 * Class to create Table object
 *
 * Last modified 04 october 2017
 *
 */

public class DBTable
{
    public DBTable() {}

    private String tableName;
    private String[] columnsName;
    private String[] dataTypes;
    private ArrayList<String[]> justDataWithoutMetaData;
    private String primaryKey;
    private String seperatorMetaDataAndData;

    public void setTableName(String tableName) { this.tableName = tableName; }

    public void setColumnsName(String[] columnsName) { this.columnsName = columnsName; }

    public void setDataTypes(String[] dataTypes) { this.dataTypes = dataTypes; }

    public void setJustDataWithoutMetaData(ArrayList<String[]> justDataWithoutMetaData) { this.justDataWithoutMetaData = justDataWithoutMetaData; }

    public void setPrimaryKey(String primaryKey) { this.primaryKey = primaryKey; }

    public void setSeperatorMetaDataAndData(String seperatorMetaDataAndData) { this.seperatorMetaDataAndData = seperatorMetaDataAndData; }

    public String getTableName() { return tableName; }

    public String[] getColumnsName() {return columnsName;}

    public String[] getDataTypes() {return dataTypes;}

    public ArrayList<String[]> getJustDataWithoutMetaData() {return justDataWithoutMetaData;}

    public String getPrimaryKey() { return primaryKey; }
}
