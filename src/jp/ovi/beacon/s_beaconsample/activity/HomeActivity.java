package jp.ovi.beacon.s_beaconsample.activity;

import jp.ovi.beacon.s_beaconsample.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

@SuppressLint("NewApi")
public class HomeActivity extends Activity {
	private BluetoothAdapter mBluetoothAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();
		mBluetoothAdapter.startLeScan(mLeScanCallback);

	}

	private BluetoothAdapter.LeScanCallback mLeScanCallback = new LeScanCallback() {
		@Override
		public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
			// TODO -> rssi ってのが電波の強さ　計算方法がどっかにあったので拾ってくる
			if (scanRecord.length > 30) {
				if ((scanRecord[5] == (byte) 0x4c) && (scanRecord[6] == (byte) 0x00) && (scanRecord[7] == (byte) 0x02)
						&& (scanRecord[8] == (byte) 0x15)) {
					String uuid = intToHex2(scanRecord[9] & 0xff) + intToHex2(scanRecord[10] & 0xff)
							+ intToHex2(scanRecord[11] & 0xff) + intToHex2(scanRecord[12] & 0xff) + "-"
							+ intToHex2(scanRecord[13] & 0xff) + intToHex2(scanRecord[14] & 0xff) + "-"
							+ intToHex2(scanRecord[15] & 0xff) + intToHex2(scanRecord[16] & 0xff) + "-"
							+ intToHex2(scanRecord[17] & 0xff) + intToHex2(scanRecord[18] & 0xff) + "-"
							+ intToHex2(scanRecord[19] & 0xff) + intToHex2(scanRecord[20] & 0xff)
							+ intToHex2(scanRecord[21] & 0xff) + intToHex2(scanRecord[22] & 0xff)
							+ intToHex2(scanRecord[23] & 0xff) + intToHex2(scanRecord[24] & 0xff);

					String major = intToHex2(scanRecord[25] & 0xff) + intToHex2(scanRecord[26] & 0xff);
					String minor = intToHex2(scanRecord[27] & 0xff) + intToHex2(scanRecord[28] & 0xff);
					
					System.out.println("uuid = " + uuid + " major = " + major + " minor = " + minor);
				}
			}
		};

	};

	public String intToHex2(int i) {
		char hex_2[] = { Character.forDigit((i >> 4) & 0x0f, 16), Character.forDigit(i & 0x0f, 16) };
		String hex_2_str = new String(hex_2);
		return hex_2_str.toUpperCase();
	}

}