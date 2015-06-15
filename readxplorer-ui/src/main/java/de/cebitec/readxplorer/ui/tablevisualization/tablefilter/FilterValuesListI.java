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

package de.cebitec.readxplorer.ui.tablevisualization.tablefilter;

import de.cebitec.readxplorer.utils.ListTableModel;
import java.util.List;




/**
 * Interface for filtering values of a table row by a given cutoff
 *
 * @author Rolf Hilker <rhilker at cebitec.uni-bielefeld.de>, kstaderm
 */
public interface FilterValuesListI {

    /**
     * Filters a table row according to the given cutoff and current entry
     * value.
     * <p>
     * @param filteredTableModel the table model to filter
     * @param row                the currently checked row
     * @param cutoff             the cutoff value
     * @param currentEntryValue  the current entry value from the row
     */
    void filterTable( ListTableModel filteredTableModel, List<Object> row, double cutoff, double currentEntryValue );


}
