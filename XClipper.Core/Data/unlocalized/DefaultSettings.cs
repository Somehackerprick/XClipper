﻿using System.Windows;
using System.IO;
using IniParser;
using System.ComponentModel;
using IniParser.Model;
using System;

namespace Components
{
    public static class DefaultSettings
    {
        #region Variable Definitions

        private static string SettingsPath = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "XClipper.ini");
        private const string SETTINGS = "Settings";

        #endregion


        #region Actual Settings

        // This will set the start location of the app.
        public static XClipperLocation AppDisplayLocation { get; set; } = XClipperLocation.BottomRight;
        // This tells what to store meaning only Text, Image, Files or All.
        public static XClipperStore WhatToStore { get; set; } = XClipperStore.All;
        // This tells the number of clip to store.
        public static int TotalClipLength { get; set; } = 20;
        // This tells if Ctrl needs to be pressed in order to activate application.
        public static bool IsCtrl { get; set; } = true;
        // This tells if Alt needs to be pressed in order to activate application.
        public static bool IsAlt { get; set; } = false;
        // This tells if Shift needs to be pressed in order to activate application.
        public static bool IsShift { get; set; } = false;
        // This will set Final Hot key of application.
        public static string HotKey { get; set; } = "Oem3";
        // This will tell if application should start on System Startup.
        public static bool StartOnSystemStartup { get; set; } = true;
        // This will tell if application should play sound when started.
        public static bool PlayNotifySound { get; set; } = true;

        #endregion


        #region Methods

        private static FileIniDataParser InitParser()
        {
            var parser = new FileIniDataParser();
            return parser;
        }
        public static void WriteSettings()
        {
            var parser = InitParser();
            var data = new IniData();
            data[SETTINGS][nameof(AppDisplayLocation)] = AppDisplayLocation.ToString();
            data[SETTINGS][nameof(WhatToStore)] = WhatToStore.ToString();
            data[SETTINGS][nameof(TotalClipLength)] = TotalClipLength.ToString();
            data[SETTINGS][nameof(IsCtrl)] = IsCtrl.ToString();
            data[SETTINGS][nameof(IsAlt)] = IsAlt.ToString();
            data[SETTINGS][nameof(IsShift)] = IsShift.ToString();
            data[SETTINGS][nameof(HotKey)] = HotKey;
            data[SETTINGS][nameof(StartOnSystemStartup)] = StartOnSystemStartup.ToString();
            data[SETTINGS][nameof(PlayNotifySound)] = PlayNotifySound.ToString();
            parser.WriteFile(SettingsPath, data);
        }

        public static void LoadSettings()
        {
            if (!File.Exists(SettingsPath)) return;  // Return if settings does not exist, so it will use defaults
            var parser = InitParser();
            var data = parser.ReadFile(SettingsPath);
            AppDisplayLocation = data[SETTINGS][nameof(AppDisplayLocation)].ToEnum<XClipperLocation>();
            WhatToStore = data[SETTINGS][nameof(WhatToStore)].ToEnum<XClipperStore>();
            TotalClipLength = data[SETTINGS][nameof(TotalClipLength)].ToInt();
            IsCtrl = data[SETTINGS][nameof(IsCtrl)].ToBool();
            IsAlt = data[SETTINGS][nameof(IsAlt)].ToBool();
            IsShift = data[SETTINGS][nameof(IsShift)].ToBool();
            HotKey = data[SETTINGS][nameof(HotKey)];
            StartOnSystemStartup = data[SETTINGS][nameof(StartOnSystemStartup)].ToBool();
            PlayNotifySound = data[SETTINGS][nameof(PlayNotifySound)].ToBool();
        }

        #endregion
    }

    #region Setting Enums

    public enum XClipperStore
    {
        [Description("Text Only")]
        Text = 0,
        [Description("Image Only")]
        Image = 1,
        [Description("Files Only")]
        Files = 2,
        [Description("Everything")]
        All = 3
    }

    public enum XClipperLocation
    {
        [Description("Bottom Right")]
        BottomRight = 0,
        [Description("Bottom Left")]
        BottomLeft = 1,
        [Description("Top Right")]
        TopRight = 2,
        [Description("Top Left")]
        TopLeft = 3,
        [Description("Center")]
        Center = 4
    }

    #endregion
}
