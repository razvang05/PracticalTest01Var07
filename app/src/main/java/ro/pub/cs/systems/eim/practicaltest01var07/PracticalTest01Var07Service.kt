package ro.pub.cs.systems.eim.practicaltest01var07

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlin.concurrent.thread
import kotlin.math.PI
import kotlin.random.Random

class PracticalTest01Var07Service : Service(){

    private var isrunnig = false
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(!isrunnig) {
            isrunnig = true
            thread(name = "RandomBroadcast") {
                while(isrunnig) {
                    val v1 = Random.nextInt(0,100)
                    val v2 = Random.nextInt(0,100)
                    val v3 = Random.nextInt(0,100)
                    val v4 = Random.nextInt(0,100)

                    val b = Intent(Constants.ACTION_RANDOM).apply {
                        putExtra(Constants.V1,v1)
                        putExtra(Constants.V2,v2)
                        putExtra(Constants.V3,v3)
                        putExtra(Constants.V4,v4)
                        setPackage(packageName)
                    }
                    sendBroadcast(b)
                    try { Thread.sleep(10_000) } catch (_: InterruptedException) { }
                }
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
       return null
    }

    override fun onDestroy() {
        isrunnig = false
        super.onDestroy()
    }
}