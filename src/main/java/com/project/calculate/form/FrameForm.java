package com.project.calculate.form;

public class FrameForm {

    private int height;                             //Высота этажа
    private double perimeter_of_external_walls;     //Периметр внешних стен
    private double base_area;                       //Площидь основания
    private double external_wall_thickness;         //Толщина внешних стен
    private double internal_wall_length;            //Длина внутренних стен
    private double internal_wall_thickness;         //Толщина внутренних стен
    private String OSB_external_wall;               //ОСБ внешних стен
    private String steam_waterproofing_external;    //Парогидроизоляция внешних стен
    private String windscreen_external_wall;        //Ветрозащита внешних стен
    private String insulation_external_wall;        //Утеплитель внешних стен
    private String OSB_internal_wal;                //ОСБ внутренних стен
    private String OSB_thickness;                   //ОСБ перекрытия
    private String steam_waterproofing_thickness;   //Парогидроизоляция перекрытия
    private String windscreen_thickness;            //Ветрозащита перекрытия
    private String insulation__thickness;           //Утеплитель перекрытия
    private int overlap_thickness;               //Толщина перекрытия
    private int floor_number;                       //Номер этажа

    public FrameForm(){}

    public FrameForm(int height, double perimeter_of_external_walls, double base_area, double external_wall_thickness, double internal_wall_length, double internal_wall_thickness, String OSB_external_wall, String steam_waterproofing_external, String windscreen_external_wall, String insulation_external_wall, String OSB_internal_wal, String OSB_thickness, String steam_waterproofing_thickness, String windscreen_thickness, String insulation__thickness, int overlap_thickness, int floor_number) {
        this.height = height;
        this.perimeter_of_external_walls = perimeter_of_external_walls;
        this.base_area = base_area;
        this.external_wall_thickness = external_wall_thickness;
        this.internal_wall_length = internal_wall_length;
        this.internal_wall_thickness = internal_wall_thickness;
        this.OSB_external_wall = OSB_external_wall;
        this.steam_waterproofing_external = steam_waterproofing_external;
        this.windscreen_external_wall = windscreen_external_wall;
        this.insulation_external_wall = insulation_external_wall;
        this.OSB_internal_wal = OSB_internal_wal;
        this.OSB_thickness = OSB_thickness;
        this.steam_waterproofing_thickness = steam_waterproofing_thickness;
        this.windscreen_thickness = windscreen_thickness;
        this.insulation__thickness = insulation__thickness;
        this.overlap_thickness = overlap_thickness;
        this.floor_number = floor_number;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getPerimeter_of_external_walls() {
        return perimeter_of_external_walls;
    }

    public void setPerimeter_of_external_walls(double perimeter_of_external_walls) {
        this.perimeter_of_external_walls = perimeter_of_external_walls;
    }

    public double getBase_area() {
        return base_area;
    }

    public void setBase_area(double base_area) {
        this.base_area = base_area;
    }

    public double getExternal_wall_thickness() {
        return external_wall_thickness;
    }

    public void setExternal_wall_thickness(double external_wall_thickness) {
        this.external_wall_thickness = external_wall_thickness;
    }

    public double getInternal_wall_length() {
        return internal_wall_length;
    }

    public void setInternal_wall_length(double internal_wall_length) {
        this.internal_wall_length = internal_wall_length;
    }

    public double getInternal_wall_thickness() {
        return internal_wall_thickness;
    }

    public void setInternal_wall_thickness(double internal_wall_thickness) {
        this.internal_wall_thickness = internal_wall_thickness;
    }

    public String getOSB_external_wall() {
        return OSB_external_wall;
    }

    public void setOSB_external_wall(String OSB_external_wall) {
        this.OSB_external_wall = OSB_external_wall;
    }

    public String getSteam_waterproofing_external() {
        return steam_waterproofing_external;
    }

    public void setSteam_waterproofing_external(String steam_waterproofing_external) {
        this.steam_waterproofing_external = steam_waterproofing_external;
    }

    public String getWindscreen_external_wall() {
        return windscreen_external_wall;
    }

    public void setWindscreen_external_wall(String windscreen_external_wall) {
        this.windscreen_external_wall = windscreen_external_wall;
    }

    public String getInsulation_external_wall() {
        return insulation_external_wall;
    }

    public void setInsulation_external_wall(String insulation_external_wall) {
        this.insulation_external_wall = insulation_external_wall;
    }

    public String getOSB_internal_wal() {
        return OSB_internal_wal;
    }

    public void setOSB_internal_wal(String OSB_internal_wal) {
        this.OSB_internal_wal = OSB_internal_wal;
    }

    public String getOSB_thickness() {
        return OSB_thickness;
    }

    public void setOSB_thickness(String OSB_thickness) {
        this.OSB_thickness = OSB_thickness;
    }

    public String getSteam_waterproofing_thickness() {
        return steam_waterproofing_thickness;
    }

    public void setSteam_waterproofing_thickness(String steam_waterproofing_thickness) {
        this.steam_waterproofing_thickness = steam_waterproofing_thickness;
    }

    public String getWindscreen_thickness() {
        return windscreen_thickness;
    }

    public void setWindscreen_thickness(String windscreen_thickness) {
        this.windscreen_thickness = windscreen_thickness;
    }

    public String getInsulation__thickness() {
        return insulation__thickness;
    }

    public void setInsulation__thickness(String insulation__thickness) {
        this.insulation__thickness = insulation__thickness;
    }

    public int getOverlap_thickness() {
        return overlap_thickness;
    }

    public void setOverlap_thickness(int overlap_thickness) {
        this.overlap_thickness = overlap_thickness;
    }

    public int getFloor_number() {
        return floor_number;
    }

    public void setFloor_number(int floor_number) {
        this.floor_number = floor_number;
    }
}
