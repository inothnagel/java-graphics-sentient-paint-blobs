# Sentient Paint Blobs from Outer Space!

Experimenting with interactive graphics animations style and performance in Java Swing and AWT.

Creates a swarm of colour-changing paint blobs that follow the cursor.

### Ideas
 - Go complete fullscreen
 - Keep track of frame rate and display it in the diagnostics
 - Keep adding blobs until the frame rate hits an acceptable minimum
 - Allow the user to fiddle with the parameters
 - Allow the user to save and load parameter configurations
 
### Learnings so far
 - Canvas is surprisingly fast when you double-buffer, even on old hardware.
 - Decoupling logical and display coordinates makes for simpler smoother animations.