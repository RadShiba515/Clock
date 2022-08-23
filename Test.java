import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Made by Cole McCorkendale!
 * This is a clock I made in my free time using the stdlib library from Princeton.
 * It's a very limited graphics library but I still made it work, kinda.
 *
 * IMPORTANT: This requires the stdlib library to run. Since we implemented it through
 * putting the library in the project folder, you may wish to download it to test.
 * It can be found at: https://introcs.cs.princeton.edu/java/stdlib/
**/

public class Test {
    static Calendar now;

    public static void main(String[] args) {
        // This solution involves a 2D array containing every ordered pair representing the locations on the clock
        // corresponding to each second. These were found with the negative cosine and positive sine (respectively) of
        // each integer multiplied by 6, for the angle one second represents
        final double[][] clockLocations = {
                { /* 1 */ 0.0, 1.0         }, { /* 2 */ 0.1045, 0.9945    }, { /* 3 */ 0.2079, 0.9781    },
                { /* 4 */ 0.309, 0.9511    }, { /* 5 */ 0.4067, 0.9135    }, { /* 6 */ 0.5, 0.866        },
                { /* 7 */ 0.5878, 0.809    }, { /* 8 */ 0.6691, 0.7431    }, { /* 9 */ 0.7431, 0.6691    },
                { /* 10 */ 0.809, 0.5878   }, { /* 11 */ 0.866, 0.5       }, { /* 12 */ 0.9135, 0.4067   },
                { /* 13 */ 0.9511, 0.309   }, { /* 14 */ 0.9781, 0.2079   }, { /* 15 */ 0.9945, 0.1045   },
                { /* 16 */ 1.0, 0.0        }, { /* 17 */ 0.9945, -0.1045  }, { /* 18 */ 0.9781, -0.2079  },
                { /* 19 */ 0.9511, -0.309  }, { /* 20 */ 0.9135, -0.4067  }, { /* 21 */ 0.866, -0.5      },
                { /* 22 */ 0.809, -0.5878  }, { /* 23 */ 0.7431, -0.6691  }, { /* 24 */ 0.6691, -0.7431  },
                { /* 25 */ 0.5878, -0.809  }, { /* 26 */ 0.5, -0.866      }, { /* 27 */ 0.4067, -0.9135  },
                { /* 28 */ 0.309, -0.9511  }, { /* 29 */ 0.2079, -0.9781  }, { /* 30 */ 0.1045, -0.9945  },
                { /* 31 */ 0.0, -1.0       }, { /* 32 */ -0.1045, -0.9945 }, { /* 33 */ -0.2079, -0.9781 },
                { /* 34 */ -0.309, -0.9511 }, { /* 35 */ -0.4067, -0.9135 }, { /* 36 */ -0.5, -0.866     },
                { /* 37 */ -0.5878, -0.809 }, { /* 38 */ -0.6691, -0.7431 }, { /* 39 */ -0.7431, -0.6691 },
                { /* 40 */ -0.809, -0.5878 }, { /* 41 */ -0.866, -0.5     }, { /* 42 */ -0.9135, -0.4067 },
                { /* 43 */ -0.9511, -0.309 }, { /* 44 */ -0.9781, -0.2079 }, { /* 45 */ -0.9945, -0.1045 },
                { /* 46 */ -1.0, 0.0       }, { /* 47 */ -0.9945, 0.1045  }, { /* 48 */ -0.9781, 0.2079  },
                { /* 49 */ -0.9511, 0.309  }, { /* 50 */ -0.9135, 0.4067  }, { /* 51 */ -0.866, 0.5      },
                { /* 52 */ -0.809, 0.5878  }, { /* 53 */ -0.7431, 0.6691  }, { /* 54 */ -0.6691, 0.7431  },
                { /* 55 */ -0.5878, 0.809  }, { /* 56 */ -0.5, 0.866      }, { /* 57 */ -0.4067, 0.9135  },
                { /* 58 */ -0.309, 0.9511  }, { /* 59 */ -0.2079, 0.9781  }, { /* 60 */ -0.1045, 0.9945  },
        };
        // This is the quick hacked-together code I wrote to make that table with the least amount of effort. It
        // calculates the values and rounds them to 4 decimals so the draw method has even less thinking to do.
//        for(int i = 0; i < 60; i++) {
//            double a = Math.toRadians(6 * i) + Math.toRadians(90);
//            StdOut.println("{ /* " + (i + 1) + " */ " + ((double)Math.round(-Math.cos(a) * 10000) / 10000) + ", " + ((double)Math.round(Math.sin(a) * 10000) / 10000) + " },");
//        }
        StdDraw.setCanvasSize(672, 672);
        StdDraw.setScale(-1.2, 1.2);
        StdDraw.enableDoubleBuffering();
        scheduledDraw(clockLocations);
    }

    private static void scheduledDraw(double[][] clockLocations) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
        Runnable drawSec = () -> {

            now = Calendar.getInstance();

            int sec = now.get(Calendar.SECOND) + 1;
            if(sec == 60) sec = 0;
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(0, 0, clockLocations[sec][0], clockLocations[sec][1]);

            int min = now.get(Calendar.MINUTE);
            StdDraw.setPenColor(StdDraw.BOOK_BLUE);
            StdDraw.line(0, 0, clockLocations[min][0], clockLocations[min][1]);

            int hr = now.get(Calendar.HOUR);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.line(0, 0, clockLocations[Math.max(((hr * 5) - 1), 0)][0], clockLocations[Math.max(((hr * 5) - 1), 0)][1]);
            // These Math.maxes were added much after the fact after realizing at midnight the value was -1. Not sure,
            // but it probably isn't the most efficient method.

            StdDraw.show();
            StdDraw.clear();

        };
        executor.scheduleAtFixedRate(drawSec, 0, 1, TimeUnit.SECONDS);
    }
}