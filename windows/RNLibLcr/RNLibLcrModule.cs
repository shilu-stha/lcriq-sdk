using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Lib.Lcr.RNLibLcr
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNLibLcrModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNLibLcrModule"/>.
        /// </summary>
        internal RNLibLcrModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNLibLcr";
            }
        }
    }
}
