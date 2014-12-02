/*
 * Copyright (C) 2014 Kai Bernd Stadermann <kstaderm at cebitec.uni-bielefeld.de>
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
package de.cebitec.readXplorer.differentialExpression.wizard;


import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JPanel;


public final class DeSeqVisualPanel1 extends JPanel {

    private ButtonGroup btGroup = new ButtonGroup();


    /**
     * Creates new form DeSeqVisualPanel1
     */
    public DeSeqVisualPanel1() {
        initComponents();
        twoConditionsRB.getModel().setActionCommand( "two" );
        moreConditionsRB.getModel().setActionCommand( "more" );
        btGroup.add( twoConditionsRB );
        btGroup.add( moreConditionsRB );
    }


    @Override
    public String getName() {
        return "Number of conditions";
    }


    public boolean buttonChecked() {
        return (twoConditionsRB.isSelected() || moreConditionsRB.isSelected());
    }


    public boolean moreThanTwoConditions() {
        ButtonModel bm = btGroup.getSelection();
        String ac = bm.getActionCommand();
        if( ac.equals( "two" ) ) {
            return false;
        }
        else {
            return true;
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        twoConditionsRB = new javax.swing.JRadioButton();
        moreConditionsRB = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        twoConditionsRB.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(twoConditionsRB, org.openide.util.NbBundle.getMessage(DeSeqVisualPanel1.class, "DeSeqVisualPanel1.twoConditionsRB.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(moreConditionsRB, org.openide.util.NbBundle.getMessage(DeSeqVisualPanel1.class, "DeSeqVisualPanel1.moreConditionsRB.text")); // NOI18N

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(240, 240, 240));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(jTextArea1.getFont());
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText(org.openide.util.NbBundle.getMessage(DeSeqVisualPanel1.class, "DeSeqVisualPanel1.jTextArea1.text")); // NOI18N
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBorder(null);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(twoConditionsRB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 206, Short.MAX_VALUE)
                        .addComponent(moreConditionsRB)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(twoConditionsRB)
                    .addComponent(moreConditionsRB))
                .addGap(91, 91, 91))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JRadioButton moreConditionsRB;
    private javax.swing.JRadioButton twoConditionsRB;
    // End of variables declaration//GEN-END:variables
}
