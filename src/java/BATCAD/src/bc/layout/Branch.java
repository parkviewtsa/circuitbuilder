/*
 * Copyright (C) 2014 Brent Bessemer, Gokul Venkatesebaba, Asimm Hirani,
 * Seth Carter, Alexandra Marlette, Linus Fennell.
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
package layout;

/**
 *
 * @author Brent
 * 
 * Class Branch represents a branch of the circuit in the curve stage.
 * It needs to indicate which components belong on it and how it connects to other branches.
 * IDEA: Since we don't want to waste coding on a Junction class, number the junctions in the
 * diagram and indicate which junctions the branch connects to at its positive and negative
 * ends.
 */
public class Branch {
    Branch[] master;
    int id;
    Component[] components;
    int posEnd;
    int negEnd;
    
    Branch parent () {
        for (int i=0; i<master.length; i++) {
            if (master[i].negEnd == this.posEnd) {
                return master[i];
            }
        }
    }
    
    int getNumberOfLineSegments () {
        /**
         * Figure out how many line segments will be needed to draw the branch.
         * If the branch is on the outside, three
         * Otherwise (usually) one
         */
        Branch[] parallels = this.getParallelBranches(master);
        if (parallels[parallels.length].id == this.id) {
            return 3;
        } else {
            return 1;
        }
    }
    
    Branch[] getParallelBranches (Branch[] allBranches) {
        Branch[] res; //result
        /*
         * Gokul -- since you're learning loops in Java, this section of the
         * code might be a useful exercise.
         * 
         * Using a loop, search through allBranches and test each branch to see
         * if it is parallel to the current one (the one of which this is a
         * method - that is, "this". (If it is parallel, it will have the same
         * negEnd and posEnd.) If it is, append it to res.
         */
        return res;
    }
}
