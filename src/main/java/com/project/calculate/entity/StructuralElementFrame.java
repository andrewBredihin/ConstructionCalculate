package com.project.calculate.entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Structural_element_frame")
public class StructuralElementFrame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "amount_floor", nullable = false)
    private Integer amountFloor;

    @Column(name = "base_area", nullable = false)
    private Double baseArea;

    @Column(name = "external_wall_thickness", nullable = false)
    private Double externalWallThickness;

    @Column(name = "floor_height", nullable = false)
    private Integer floorHeight;

    @Column(name = "floor_number", nullable = false)
    private Integer floorNumber;

    @Column(name = "insulation_external_wall", nullable = false)
    private String insulationExternalWall;

    @Column(name = "insulation__thickness", nullable = false)
    private String insulationThickness;

    @Column(name = "internal_wall_length", nullable = false)
    private Double internalWallLength;

    @Column(name = "internal_wall_thickness", nullable = false)
    private Double internalWallThickness;

    @Column(name = "osb_external_wall", nullable = false)
    private String osbExternalWall;

    @Column(name = "osb_internal_wal", nullable = false)
    private String osbInternalWal;

    @Column(name = "osb_thickness", nullable = false)
    private String osbThickness;

    @Column(name = "overlap_thickness", nullable = false)
    private int overlapThickness;

    @Column(name = "perimeter_of_external_walls", nullable = false)
    private Double perimeterOfExternalWalls;

    @Column(name = "\"steam_waterproofing_external _wall\"", nullable = false)
    private String steamWaterproofingExternalWall;

    @Column(name = "steam_waterproofing_thicknes", nullable = false)
    private String steamWaterproofingThicknes;

    @Column(name = "windscreen_external_wall", nullable = false)
    private String windscreenExternalWall;

    @Column(name = "windscreen_thickness", nullable = false)
    private String windscreenThickness;

    @ManyToMany(mappedBy = "structuralElementFrames")
    private Set<Result> results = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "structuralElementFrames")
    private Set<Opening> openings = new LinkedHashSet<>();

    public Set<Opening> getOpenings() {
        return openings;
    }

    public void setOpenings(Set<Opening> openings) {
        this.openings = openings;
    }

    public Set<Result> getResults() {
        return results;
    }

    public void setResults(Set<Result> results) {
        this.results = results;
    }

    public StructuralElementFrame(){}

    public String getWindscreenThickness() {
        return windscreenThickness;
    }

    public void setWindscreenThickness(String windscreenThickness) {
        this.windscreenThickness = windscreenThickness;
    }

    public String getWindscreenExternalWall() {
        return windscreenExternalWall;
    }

    public void setWindscreenExternalWall(String windscreenExternalWall) {
        this.windscreenExternalWall = windscreenExternalWall;
    }

    public String getSteamWaterproofingThicknes() {
        return steamWaterproofingThicknes;
    }

    public void setSteamWaterproofingThicknes(String steamWaterproofingThicknes) {
        this.steamWaterproofingThicknes = steamWaterproofingThicknes;
    }

    public String getSteamWaterproofingExternalWall() {
        return steamWaterproofingExternalWall;
    }

    public void setSteamWaterproofingExternalWall(String steamWaterproofingExternalWall) {
        this.steamWaterproofingExternalWall = steamWaterproofingExternalWall;
    }

    public Double getPerimeterOfExternalWalls() {
        return perimeterOfExternalWalls;
    }

    public void setPerimeterOfExternalWalls(Double perimeterOfExternalWalls) {
        this.perimeterOfExternalWalls = perimeterOfExternalWalls;
    }

    public int getOverlapThickness() {
        return overlapThickness;
    }

    public void setOverlapThickness(int overlapThickness) {
        this.overlapThickness = overlapThickness;
    }

    public String getOsbThickness() {
        return osbThickness;
    }

    public void setOsbThickness(String osbThickness) {
        this.osbThickness = osbThickness;
    }

    public String getOsbInternalWal() {
        return osbInternalWal;
    }

    public void setOsbInternalWal(String osbInternalWal) {
        this.osbInternalWal = osbInternalWal;
    }

    public String getOsbExternalWall() {
        return osbExternalWall;
    }

    public void setOsbExternalWall(String osbExternalWall) {
        this.osbExternalWall = osbExternalWall;
    }

    public Double getInternalWallThickness() {
        return internalWallThickness;
    }

    public void setInternalWallThickness(Double internalWallThickness) {
        this.internalWallThickness = internalWallThickness;
    }

    public Double getInternalWallLength() {
        return internalWallLength;
    }

    public void setInternalWallLength(Double internalWallLength) {
        this.internalWallLength = internalWallLength;
    }

    public String getInsulationThickness() {
        return insulationThickness;
    }

    public void setInsulationThickness(String insulationThickness) {
        this.insulationThickness = insulationThickness;
    }

    public String getInsulationExternalWall() {
        return insulationExternalWall;
    }

    public void setInsulationExternalWall(String insulationExternalWall) {
        this.insulationExternalWall = insulationExternalWall;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Integer getFloorHeight() {
        return floorHeight;
    }

    public void setFloorHeight(Integer floorHeight) {
        this.floorHeight = floorHeight;
    }

    public Double getExternalWallThickness() {
        return externalWallThickness;
    }

    public void setExternalWallThickness(Double externalWallThickness) {
        this.externalWallThickness = externalWallThickness;
    }

    public Double getBaseArea() {
        return baseArea;
    }

    public void setBaseArea(Double baseArea) {
        this.baseArea = baseArea;
    }

    public Integer getAmountFloor() {
        return amountFloor;
    }

    public void setAmountFloor(Integer amountFloor) {
        this.amountFloor = amountFloor;
    }
}