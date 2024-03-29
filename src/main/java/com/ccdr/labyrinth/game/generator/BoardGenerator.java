package com.ccdr.labyrinth.game.generator;

import com.ccdr.labyrinth.game.GameBoard;
import com.ccdr.labyrinth.game.tiles.Board;
import com.ccdr.labyrinth.game.tiles.Tile;
import com.ccdr.labyrinth.game.tiles.GuildTile;
import com.ccdr.labyrinth.game.tiles.SourceTile;
import com.ccdr.labyrinth.game.tiles.StandardTile;
import com.ccdr.labyrinth.game.util.Coordinate;
import com.ccdr.labyrinth.game.util.Direction;
import com.ccdr.labyrinth.game.util.Material;

import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Random;
import java.util.Optional;

/**
 * BoardGenerator contains all the logic to generate a randomic board following some preconcepts:
 * -> The guild tile is always placed at the center of the labyrinth.
 * -> The source tiles are placed circular around the guild tile.
 * -> The normal tiles are placed randomically and with a probability to contain a bonus material inside.
 */
public final class BoardGenerator {
    private static final int MIN_PATTERN_SELECTOR = 0, MAX_PATTERN_SELECTOR = 4;
    private final int height, width, sourceNumber, playerNum;
    private final Random seed;
    private final CoordinateGenerator placer;
    private final Set<Coordinate> playersLocation;
    private final List<Material> materials;
    private final List<Optional<Material>> bonuses;

    /**
     * The constructor of BoardGenerator that sets up all necessary informations
     * to generate the board correctly.
     * @param h the height of the labyrinth.
     * @param w the width of the labyrinth.
     * @param sources sources number.
     * @param players players number.
     * @param materials the list of used materials.
     */
    public BoardGenerator(final int h, final int w, final int sources, final int players, final List<Material> materials) {
        this.sourceNumber = sources;
        this.playerNum = players;
        this.height = h;
        this.width = w;
        this.playersLocation = Set.of(
            new Coordinate(0, 0),
            new Coordinate(0, this.width - 1),
            new Coordinate(this.height - 1, 0),
            new Coordinate(this.height - 1, this.width - 1)
        );
        this.placer = new CoordinateGenerator(this.height, this.width, this.sourceNumber);
        this.seed = new Random();
        this.materials = setupMaterialsList(materials);
        this.bonuses = this.setupBonusList(materials);
    }

    /**
     * The main method of this class, which based on the information taken from the constructor
     * executes all calls to internal methods used to generate all interested objects.
     * @param maxPoints the number of max points given by missions completion. 
     * @return fully generated labyrinth game board.
     */
    public Board generate(final int maxPoints) {
        //Parameters that depend on the config
        final Board tiles = new GameBoard();
        final Coordinate center = new Coordinate(height / 2, width / 2);
        int normalQuantity = height * width - this.sourceNumber - 1;
        //Guild tile generation
        final GuildTile guild = new GuildTile(maxPoints);
        guild.setPattern(selectPattern(4));
        tiles.insertTile(center, guild);
        tiles.addBlocked(center);
        //Source Tiles generation
        final List<Coordinate> sourceCoordinates = new ArrayList<>();
        sourceCoordinates.addAll(this.placer.calculateSourcesCoordinates(center, tiles.getMap()));
        int index = sourceCoordinates.size() - 1;
        Coordinate sourceGeneratedCoordinate;
        for (final Material m : this.materials) {
            sourceGeneratedCoordinate = sourceCoordinates.remove(index--);
            tiles.addBlocked(sourceGeneratedCoordinate);
            tiles.insertTile(sourceGeneratedCoordinate, generateSource(m, this.playerNum));
        }
        //Normal and bonus tiles generation
        while (normalQuantity > 0) {
            final Tile generatedTile;
            Coordinate generatedCoordinate;
            Optional<Material> sourcesMaterial;
            generatedCoordinate = this.placer.generateRandomCoordinate(tiles.getMap());
            sourcesMaterial = this.pickMaterial(this.bonuses);
            generatedTile = this.generateStandardTile(sourcesMaterial, generatedCoordinate);
            generatedTile.setPattern(generateRandomPattern().getPattern());
            tiles.insertTile(generatedCoordinate, generatedTile);
            normalQuantity--; 
        }
        return tiles;
    }

    private StandardTile generateStandardTile(final Optional<Material> bonus, final Coordinate coordinates) {
        if (bonus.isEmpty() || this.playersLocation.contains(coordinates)) {
            return new StandardTile();
        } else {
            return new StandardTile(bonus.get(), 1);
        }
    }

    private Optional<Material> pickMaterial(final List<Optional<Material>> bonuses) {
        if (!bonuses.isEmpty()) {
            return bonuses.get(seed.nextInt(0, bonuses.size()));
        } else {
            return Optional.empty();
        }
    }

    private List<Optional<Material>> setupBonusList(final List<Material> materialPresents) {
        final List<Optional<Material>> bonuses = new ArrayList<>();
        int percentage;
        if (!materialPresents.isEmpty()) {
            percentage = materialPresents.size() * 4;
            for (final Material m : materialPresents) {
                bonuses.add(Optional.of(m));
            }
            while (percentage > 0) {
                bonuses.add(Optional.empty());
                percentage--;
            }
            return bonuses;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Tile generateSource(final Material material, final int playerCount) {
        final Tile generatedTile = new SourceTile(material, playerCount);
        generatedTile.setPattern(generateRandomPattern().getPattern());
        return generatedTile;
    }

    private Tile generateRandomPattern() {
        final Tile pattern = new StandardTile();
        final int patternSelector = seed.nextInt(MIN_PATTERN_SELECTOR, MAX_PATTERN_SELECTOR);
        int rotations;
        rotations = seed.nextInt(MIN_PATTERN_SELECTOR, MAX_PATTERN_SELECTOR);
        pattern.setPattern(selectPattern(patternSelector));
        /* the random number of rotations allows to have all possible tile patterns given the predetermined ones */
        while (rotations > 0) {
            pattern.rotate(true);
            rotations--;
        }
        return pattern;
    }

    private Map<Direction, Boolean> selectPattern(final int selected) {
        /* PREDETERMINED TILE PATTERNS SELECTOR */
        switch (selected) {
            /* straight two-way pattern */
            case 0:
                return Map.of(
                    Direction.UP, true,
                    Direction.RIGHT, false,
                    Direction.DOWN, true,
                    Direction.LEFT, false
                );
            /* 90 degree two way pattern */
            case 1:
                return Map.of(
                    Direction.UP, true,
                    Direction.RIGHT, true,
                    Direction.DOWN, false,
                    Direction.LEFT, false
                );
            /* three-way pattern */
            case 2:
                return Map.of(
                    Direction.UP, true,
                    Direction.RIGHT, true,
                    Direction.DOWN, true,
                    Direction.LEFT, false
                );
            /* four-way pattern */
            case 3:
                return Map.of(
                    Direction.UP, true,
                    Direction.RIGHT, true,
                    Direction.DOWN, true,
                    Direction.LEFT, true
                );
            default:
                return this.selectPattern(0);
        }
    }

    /**
     * Generates a list of materials that will be used to place the source tiles in the map.
     * @param presents list of mission related materials.
     * @return redundant list of materials repeated n times where n is the number of sources per material.
     */
    private List<Material> setupMaterialsList(final List<Material> presents) {
        final List<Material> materials = new ArrayList<>();
        if (!presents.isEmpty()) {
            final int sourceEach;
            sourceEach = this.sourceNumber / presents.size();
            for (int i = sourceEach; i > 0; i--) {
                for (final Material m : presents) {
                    materials.add(m);
                }
            }
            return materials;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
