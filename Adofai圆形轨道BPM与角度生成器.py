import time

def calculate_bpm(n, straight_bpm):
    # 计算每个轨道的角度间隔
    angle_step = 360 / n

    # 计算第一个轨道的起始角度（中心角度）
    start_angle = angle_step / 2

    # 判断是内圈还是外圈
    if start_angle < 180:
        # 内圈：需要降低轨道BPM以保持实际BPM为直线BPM
        inner_bpm = straight_bpm * (1 - start_angle / 180)
        # 外圈BPM计算，基于原始BPM和内圈BPM的差值
        outer_bpm = straight_bpm + (straight_bpm - inner_bpm)
    else:
        # 外圈：需要增加轨道BPM以保持实际BPM为直线BPM
        outer_bpm = straight_bpm * ((start_angle / 180) - 1)
        # 内圈BPM计算，基于原始BPM和外圈BPM的差值
        inner_bpm = straight_bpm - (outer_bpm - straight_bpm)

    # 确保误差在±0.005以内
    inner_bpm_rounded = round(inner_bpm, 3)
    outer_bpm_rounded = round(outer_bpm, 3)

    return {
        '轨道编号': 1,
        '轨道角度': round(start_angle, 2),
        '内圈BPM': inner_bpm_rounded,
        '外圈BPM': outer_bpm_rounded,
        '内圈角度': round(180 - start_angle, 2),
        '外圈角度': round(180 + start_angle, 2)
    }


def main():
    n = int(input("请输入轨道总数n: "))
    straight_bpm = float(input("请输入直线BPM: "))

    result = calculate_bpm(n, straight_bpm)

    print("\n轨道BPM计算结果：")
    print(f"直线BPM: {straight_bpm}")
    print(f"轨道总数: {n}")
    print("\n轨道编号 | 轨道角度 | 内圈BPM | 外圈BPM | 内圈角度 | 外圈角度")
    print("-" * 65)
    print(f"{result['轨道编号']:>4} | {result['轨道角度']:>6}° | {result['内圈BPM']:>6} | {result['外圈BPM']:>6} | {result['内圈角度']:>6}° | {result['外圈角度']:>6}°")
    print("\n30秒后自动退出")
    time.sleep(30)
    exit()

if __name__ == "__main__":
    main()