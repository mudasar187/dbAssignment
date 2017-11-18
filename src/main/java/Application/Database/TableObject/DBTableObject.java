package Application.Database.TableObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <p>DBTableObject class.</p>
 *
 * @author Mudasar Ahmad
 * @version 1.0
 * <p>
 * Class to create Table object from files
 * <p>
 * Last modified 18 november 2017
 */
public class DBTableObject {
    /**
     * <p>Constructor for DBTableObject.</p>
     */
    public DBTableObject() {

    }

    private String tableName;
    private String[] columnsName;
    private String[] dataTypes;
    private ArrayList<String[]> justDataWithoutMetaData;
    private String[] primaryKey;
    private String seperatorMetaDataAndData;

    /**
     * <p>Setter for the field <code>tableName</code>.</p>
     *
     * @param tableName a {@link java.lang.String} object.
     */
    public void setTableName(String tableName) {

        this.tableName = tableName;
    }

    /**
     * <p>Setter for the field <code>columnsName</code>.</p>
     *
     * @param columnsName an array of {@link java.lang.String} objects.
     */
    public void setColumnsName(String[] columnsName) {

        this.columnsName = columnsName;
    }

    /**
     * <p>Setter for the field <code>dataTypes</code>.</p>
     *
     * @param dataTypes an array of {@link java.lang.String} objects.
     */
    public void setDataTypes(String[] dataTypes) {

        this.dataTypes = dataTypes;
    }

    /**
     * <p>Setter for the field <code>justDataWithoutMetaData</code>.</p>
     *
     * @param justDataWithoutMetaData a {@link java.util.ArrayList} object.
     */
    public void setJustDataWithoutMetaData(ArrayList<String[]> justDataWithoutMetaData) {

        this.justDataWithoutMetaData = justDataWithoutMetaData;
    }

    /**
     * <p>Setter for the field <code>primaryKey</code>.</p>
     *
     * @param primaryKey a {@link java.lang.String} object.
     */
    public void setPrimaryKey(String[] primaryKey) {

        this.primaryKey = primaryKey;
    }

    /**
     * <p>Setter for the field <code>seperatorMetaDataAndData</code>.</p>
     *
     * @param seperatorMetaDataAndData a {@link java.lang.String} object.
     */
    public void setSeperatorMetaDataAndData(String seperatorMetaDataAndData) {

        this.seperatorMetaDataAndData = seperatorMetaDataAndData;
    }

    /**
     * <p>Getter for the field <code>tableName</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getTableName() {

        return tableName;
    }

    /**
     * <p>Getter for the field <code>columnsName</code>.</p>
     *
     * @return an array of {@link java.lang.String} objects.
     */
    public String[] getColumnsName() {

        return columnsName;
    }

    /**
     * <p>Getter for the field <code>dataTypes</code>.</p>
     *
     * @return an array of {@link java.lang.String} objects.
     */
    public String[] getDataTypes() {

        return dataTypes;
    }

    /**
     * <p>Getter for the field <code>justDataWithoutMetaData</code>.</p>
     *
     * @return a {@link java.util.ArrayList} object.
     */
    public ArrayList<String[]> getJustDataWithoutMetaData() {

        return justDataWithoutMetaData;
    }

    /**
     * <p>Getter for the field <code>primaryKey</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String[] getPrimaryKey() {

        return primaryKey;
    }

    /**
     * Streams to get the list of rows in table (Java 8 function learned from Visma lecture)
     * @return list of content
     */
    public String toString()
    {
        ArrayList<String[]> content = justDataWithoutMetaData;

        String returnContent = content.stream()
                .map(c -> Arrays.asList(c).toString())
                .collect(Collectors.joining("\n"));

        validateOutPut(returnContent);

        return returnContent;
    }

    /**
     * Validate output of content string
     * @param content
     */
    private void validateOutPut(String content) {
        if(content.isEmpty() || content.equals("") || content == null) {
            System.out.println("\n### No match found ###");
        }
    }
}
