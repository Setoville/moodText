Set WshShell = WScript.CreateObject("WScript.Shell")
Comandline = "C:\Users\andrewseto\AppData\Roaming\Spotify\Spotify.exe"
WScript.sleep 500
CreateObject("WScript.Shell").Run("spotify:user:isetoville:playlist:4SDAlB6p1j4bYimnBW3Mki")
WScript.sleep 3000
WshShell.SendKeys " "