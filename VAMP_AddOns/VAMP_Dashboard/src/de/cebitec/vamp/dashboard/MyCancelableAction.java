package de.cebitec.vamp.dashboard;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.openide.util.Cancellable;
import org.openide.util.RequestProcessor;
import org.openide.util.Task;
import org.openide.util.TaskListener;


/**
 * An action, which can be canceled.
 *
 * @author jeff
 */
public final class MyCancelableAction implements ActionListener {

    private final static RequestProcessor RP = new RequestProcessor("interruptible tasks", 1, true);
    private final static Logger LOG = Logger.getLogger(MyCancelableAction.class.getName());
    private RequestProcessor.Task theTask = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        final ProgressHandle ph = ProgressHandleFactory.createHandle("task thats shows progress", new Cancellable() {

            @Override
            public boolean cancel() {
                return handleCancel();
            }
        });

        Runnable runnable = new Runnable() {

            private final int NUM = 60000;

            @Override
            public void run() {
                try {
                    ph.start(); //we must start the PH before we swith to determinate
                    ph.switchToDeterminate(NUM);
                    for (int i = 0; i < NUM; i++) {
                        doSomething(i);
                        ph.progress(i);
                        Thread.sleep(0); //throws InterruptedException is the task was cancelled
                    }

                } catch (InterruptedException ex) {
                    LOG.info("the task was CANCELLED");
                } 

            }

            private void doSomething(int i) {
                LOG.log(Level.INFO, "doSomething with {0}", i);                
            }
        };

        theTask = RP.create(runnable); //the task is not started yet

        theTask.addTaskListener(new TaskListener() {
            @Override
            public void taskFinished(Task task) {
                ph.finish();
            }
        });

        theTask.schedule(0); //start the task


    }

    private boolean handleCancel() {
        LOG.info("handleCancel");
        if (null == theTask) {
            return false;
        }

        return theTask.cancel();
    }
}