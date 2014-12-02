/*
 * Copyright (C) 2014 Institute for Bioinformatics and Systems Biology, University Giessen, Germany
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.cebitec.readXplorer.databackend.dataObjects;


import de.cebitec.readXplorer.util.classification.FeatureType;


/**
 * Interface for all persistent features.
 * <p>
 * @author Rolf Hilker <rhilker at cebitec.uni-bielefeld.de>
 */
public interface PersistentFeatureI {

    /**
     * @return start of the feature. Always the smaller value among start and
     *         stop.
     */
    public int getStart();


    /**
     * @return stop of the feature. Always the larger value among start and
     *         stop.
     */
    public int getStop();


    /**
     * @return type of the feature among FeatureTypes.
     */
    public FeatureType getType();


}
